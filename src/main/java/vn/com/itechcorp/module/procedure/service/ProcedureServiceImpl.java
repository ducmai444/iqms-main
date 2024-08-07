package vn.com.itechcorp.module.procedure.service;

import org.apache.commons.lang3.tuple.Pair;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import vn.com.itechcorp.base.exception.APIException;
import vn.com.itechcorp.base.service.dto.BaseDtoCreate;
import vn.com.itechcorp.base.service.impl.BaseDtoJpaServiceImpl;
import vn.com.itechcorp.module.modality.dto.ModalityDTOGet;
import vn.com.itechcorp.module.modality.model.Modality;
import vn.com.itechcorp.module.modality.repository.ModalityRepository;
import vn.com.itechcorp.module.procedure.dto.ProcedureDTOCreate;
import vn.com.itechcorp.module.procedure.dto.ProcedureDTOGet;
import vn.com.itechcorp.module.procedure.dto.ProcedureSiteFilter;
import vn.com.itechcorp.module.procedure.model.Procedure;
import vn.com.itechcorp.module.procedure.repository.ProcedureRepository;
import vn.com.itechcorp.module.site.model.Site;
import vn.com.itechcorp.module.site.repository.SiteRepository;
import vn.com.itechcorp.module.ticket.model.Ticket;
import vn.com.itechcorp.module.ticket.model.TicketStatus;
import vn.com.itechcorp.module.ticket.repository.TicketRepository;

import java.util.*;
import java.util.stream.Collectors;

@Service
@Transactional
public class ProcedureServiceImpl extends BaseDtoJpaServiceImpl<ProcedureDTOGet, Procedure, Long> implements ProcedureService {

    @Autowired
    private ProcedureRepository procedureRepository;

    @Autowired
    private TicketRepository ticketRepository;

    @Autowired
    private SiteRepository siteRepository;

    @Override
    public ProcedureRepository getRepository() {
        return procedureRepository;
    }

    @Override
    public ProcedureDTOGet convert(Procedure procedure) {
        return new ProcedureDTOGet(procedure);
    }

    Modality getFreeModality(Set<Modality> modalities, Map<Long, Long> waitingTicketCountMap) {
        if (modalities.size() == 1) return modalities.iterator().next();
        Modality result = null;
        long count = 999999999; // very large number
        for (Modality modality : modalities) {
            Long numOfWaitings = waitingTicketCountMap.get(modality.getId());
            if (numOfWaitings == null) return modality;
            if (numOfWaitings < count) {
                count = numOfWaitings;
                result = modality;
            }
        }
        return result;
    }

    Map.Entry<Modality, List<Procedure>> findSuitableModality(Map<Modality, List<Procedure>> modalities, List<Long> procedureIds, Map<Long, Long> waitingTicketCountMap) {
        // Find the ones which support largest number of procedures
        Map<Modality, List<Procedure>> filteredModalities = new HashMap<>();
        for (Map.Entry<Modality, List<Procedure>> m : modalities.entrySet()) {
            // Get the list of mutual procedures
            List<Procedure> supportedProcedures = m.getValue().stream().filter(p -> procedureIds.contains(p.getId())).collect(Collectors.toList());
            filteredModalities.put(m.getKey(), supportedProcedures);
        }

        int maxSupportedProcedures = modalities.values().stream().mapToInt(v -> v.size()).max().orElse(0);
        Set<Modality> selectedModalities = modalities.entrySet().stream().filter(x -> x.getValue().size() == maxSupportedProcedures).map(x -> x.getKey()).collect(Collectors.toSet());

        // Find the one which is the least busy.
        Modality modality = getFreeModality(selectedModalities, waitingTicketCountMap);

        return filteredModalities.entrySet().stream().filter(m -> m.getKey().getId() == modality.getId()).findFirst().orElse(null);
    }


    private Map<Long, Long> getWaitingTicketCount(List<Long> modalityIDs) {
        List<Ticket> tickets = ticketRepository.findAllByModalityIDInAndStatusNotIn(modalityIDs, Arrays.asList(TicketStatus.COMPLETED));
        Map<Long, Long> waitingTicketCount = tickets.stream().collect(Collectors.groupingBy(Ticket::getModalityID, Collectors.counting()));

        return waitingTicketCount;
    }

    /**
     * @param filter
     * @return Map<[ ProcedureID, ModalityID ]>
     */
    @Override
    public Map<Long, ModalityDTOGet> getSupportedModalities(ProcedureSiteFilter filter) {
        Site site = siteRepository.getOne(filter.getSiteID());
        if (site == null || site.getModalities() == null || site.getModalities().isEmpty())
            return new HashMap<>();

        List<Procedure> procedures = procedureRepository.findAllById(filter.getIds());
        Map<Modality, List<Procedure>> modalities = procedures.stream().flatMap(p -> p.getModalities().stream().map(m -> Pair.of(m, p)))
                .collect(Collectors.groupingBy(Pair::getKey, Collectors.mapping(Pair::getValue, Collectors.toList())));

        if (modalities == null || modalities.isEmpty()) return new HashMap<>();

        // Filter modalities
        Set<Long> supportedModalityIDs = site.getModalities().stream().map(Modality::getId).collect(Collectors.toSet());

        // Retain only modalities which are in the site and enabled
        modalities.entrySet().removeIf(m -> !supportedModalityIDs.contains(m.getKey().getId()));
        modalities.entrySet().removeIf(m -> m.getKey().getEnabled() != null && !m.getKey().getEnabled());

        if (modalities.isEmpty()) return new HashMap<>();

        if (modalities.size() == 1) {
            Map.Entry<Modality, List<Procedure>> first = modalities.entrySet().iterator().next();
            return first.getValue().stream().collect(Collectors.toMap(Procedure::getId, p -> new ModalityDTOGet(first.getKey())));
        }

        // Filter list of supported procedures by modality
        Set<Procedure> supportedProcedures = modalities.entrySet().stream().flatMap(m -> m.getValue().stream()).collect(Collectors.toSet());
        List<Long> supportedProcedureIDs = supportedProcedures.stream().map(Procedure::getId).collect(Collectors.toList());

        Map<Long, Long> waitingTicketCountMap = getWaitingTicketCount(modalities.keySet().stream().map(m -> m.getId()).collect(Collectors.toList()));

        Map<Long, ModalityDTOGet> results = new HashMap<>();
        while (!supportedProcedureIDs.isEmpty() && !modalities.isEmpty()) {
            Map.Entry<Modality, List<Procedure>> suitableModality = findSuitableModality(modalities, supportedProcedureIDs, waitingTicketCountMap);
            if (suitableModality == null) break;

            // Put into the Map
            for (Procedure procedure : suitableModality.getValue()) {
                results.putIfAbsent(procedure.getId(), new ModalityDTOGet(suitableModality.getKey()));
            }

            supportedProcedureIDs.removeIf(pID -> suitableModality.getValue().stream().anyMatch(p -> p.getId() == pID));
            modalities.remove(suitableModality.getKey());
        }

        return results;
    }

    @Override
    public ProcedureDTOGet create(BaseDtoCreate<Procedure, Long> entity) throws APIException {
        ProcedureDTOCreate obj = (ProcedureDTOCreate) entity;
        List<Long> modalityIDs = obj.getModalityIDs();

        if(modalityIDs != null && !modalityIDs.isEmpty()){
            Procedure procedure = obj.toEntry();
            Set<Modality> modalities = new HashSet<>();
            for (Long id: modalityIDs) {
                Modality m = new Modality();
                m.setId(id);
                modalities.add(m);
            }
            procedure.setModalities(modalities);
            procedureRepository.save(procedure);
            return new ProcedureDTOGet(procedure);
        }

        return super.create(entity);
    }
}
