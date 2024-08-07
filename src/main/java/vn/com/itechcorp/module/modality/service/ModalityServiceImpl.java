package vn.com.itechcorp.module.modality.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import vn.com.itechcorp.base.exception.APIException;
import vn.com.itechcorp.base.service.dto.BaseDtoCreate;
import vn.com.itechcorp.base.service.filter.BaseFilter;
import vn.com.itechcorp.base.service.filter.PageOfData;
import vn.com.itechcorp.base.service.filter.PaginationInfo;
import vn.com.itechcorp.base.service.impl.BaseDtoJpaServiceImpl;
import vn.com.itechcorp.module.modality.ModalityProxy;
import vn.com.itechcorp.module.modality.dto.ModalityDTOGet;
import vn.com.itechcorp.module.modality.dto.ModalityDtoCreate;
import vn.com.itechcorp.module.modality.dto.ModalityTypeFilter;
import vn.com.itechcorp.module.modality.model.Modality;
import vn.com.itechcorp.module.modality.model.ModalityType;
import vn.com.itechcorp.module.modality.repository.ModalityRepository;
import vn.com.itechcorp.module.modality.repository.ModalityTypeRepository;
import vn.com.itechcorp.module.site.model.Site;
import vn.com.itechcorp.module.ticket.model.Ticket;
import vn.com.itechcorp.module.ticket.model.TicketStatus;
import vn.com.itechcorp.module.ticket.repository.TicketRepository;

import java.util.*;
import java.util.stream.Collectors;

@SuppressWarnings("Duplicates")
@Service("modalityService")
public class ModalityServiceImpl extends BaseDtoJpaServiceImpl<ModalityDTOGet, Modality, Long> implements ModalityService {

    @Autowired
    private ModalityProxy modalityProxy;

    @Autowired
    private ModalityRepository modalityRepository;

    @Autowired
    private ModalityTypeRepository modalityTypeRepository;

    @Autowired
    private TicketRepository ticketRepository;

    @Override
    public ModalityRepository getRepository() {
        return modalityRepository;
    }

    @Override
    public ModalityDTOGet convert(Modality modality) {
        return new ModalityDTOGet(modality);
    }

    @Override
    public List<String> getModalityTypes() {
        List<ModalityType> modalityTypes = modalityTypeRepository.findAll();
        if (modalityTypes == null || modalityTypes.isEmpty()) return new ArrayList<>();
        return modalityTypes.stream().map(ModalityType::getId).collect(Collectors.toList());
    }

    @Override
    public Collection<String> getModalityTypes(ModalityTypeFilter filter) {
        List<Modality> modalities = null;
        if (filter.getSiteID() != null) {
            modalities = modalityRepository.findAllBySiteID(filter.getSiteID());
        } else {
            modalities = modalityRepository.findAll();
        }

        if (modalities == null || modalities.isEmpty()) return new ArrayList<>();

        return modalities.stream().map(Modality::getModalityType).collect(Collectors.toSet());
    }

    private String getCurrentTicketNumber(List<Ticket> tickets) {
        if (tickets == null || tickets.isEmpty()) return "0";

        tickets.sort((t1, t2) -> t2.getTicketNumber().compareTo(t1.getTicketNumber())); // Sort by reversed order
        return tickets.get(0).getTicketNumber();
    }

    @Override
    public void fetchLazyInformation(List<ModalityDTOGet> modalities) {
        List<Long> modalityIDs = modalities.stream().map(ModalityDTOGet::getId).collect(Collectors.toList());
        List<Ticket> tickets = ticketRepository.findAllByModalityIDIn(modalityIDs);

        Map<Long, List<Ticket>> modalityTicketsMap = tickets.stream().collect(Collectors.groupingBy(Ticket::getModalityID));

        Map<Long, Long> totalTickets = tickets.stream().collect(Collectors.groupingBy(Ticket::getModalityID, Collectors.counting()));
        Map<Long, Long> completedTickets = tickets.stream().filter(t -> t.getStatus() == TicketStatus.COMPLETED).collect(Collectors.groupingBy(Ticket::getModalityID, Collectors.counting()));

        for (ModalityDTOGet modality : modalities) {
            modality.setTotalPatients(totalTickets.getOrDefault(modality.getId(), 0L).intValue());
            modality.setTotalCompletedPatients(completedTickets.getOrDefault(modality.getId(), 0L).intValue());
            modality.setCurrentNumber(getCurrentTicketNumber(modalityTicketsMap.get(modality.getId())));
        }

    }

    @Override
    public PageOfData<ModalityDTOGet> getPageOfData(PaginationInfo pagingInfo) throws APIException {
        PageOfData<ModalityDTOGet> modalities = super.getPageOfData(pagingInfo);
        if (modalities.getElements() != null && !modalities.getElements().isEmpty())
            fetchLazyInformation(modalities.getElements());

        return modalities;
    }

    @Override
    public PageOfData<ModalityDTOGet> getPageOfData(BaseFilter<Modality> filter, PaginationInfo pagingInfo) throws APIException {
        PageOfData<ModalityDTOGet> modalities = super.getPageOfData(filter, pagingInfo);
        if (modalities.getElements() != null && !modalities.getElements().isEmpty())
            fetchLazyInformation(modalities.getElements());

        return modalities;
    }

    @Override
    public List<ModalityDTOGet> getAll(BaseFilter<Modality> filter) throws APIException {
        List<ModalityDTOGet> modalities = super.getAll(filter);
        if (!modalities.isEmpty()) fetchLazyInformation(modalities);
        return modalities;
    }

    @Override
    public List<ModalityDTOGet> getById(List<Long> ids) {
        return modalityRepository.findAllById(ids).stream().map(this::convert).collect(Collectors.toList());
    }

    @Override
    public ModalityDTOGet create(BaseDtoCreate<Modality, Long> entity) throws APIException {
        ModalityDtoCreate obj = (ModalityDtoCreate) entity;

        if(obj.getSiteID() != null){
            Modality modality = obj.toEntry();
            Site site = new Site();
            site.setId(obj.getSiteID());
            modality.setSite(site);
            modalityRepository.save(modality);
            return new ModalityDTOGet(modality);
        }

        return super.create(entity);
    }

    @Override
    public List<ModalityDTOGet> getBySiteID(Long siteID) {
        return modalityRepository.findAllBySiteID(siteID).stream().map(this::convert).collect(Collectors.toList());
    }
}

