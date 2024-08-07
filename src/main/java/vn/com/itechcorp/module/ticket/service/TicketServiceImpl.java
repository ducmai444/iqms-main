package vn.com.itechcorp.module.ticket.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import vn.com.itechcorp.base.exception.APIException;
import vn.com.itechcorp.base.exception.InvalidOperationOnObjectException;
import vn.com.itechcorp.base.service.dto.BaseDtoCreate;
import vn.com.itechcorp.base.service.filter.BaseFilter;
import vn.com.itechcorp.base.service.filter.PageOfData;
import vn.com.itechcorp.base.service.filter.PaginationInfo;
import vn.com.itechcorp.base.service.impl.AuditableDtoJpaServiceImpl;
import vn.com.itechcorp.module.modality.dto.ModalityDTOGet;
import vn.com.itechcorp.module.modality.service.ModalityService;
import vn.com.itechcorp.module.procedure.dto.ProcedureDTOGet;
import vn.com.itechcorp.module.procedure.service.ProcedureService;
import vn.com.itechcorp.module.ticket.dto.*;
import vn.com.itechcorp.module.ticket.model.Ticket;
import vn.com.itechcorp.module.ticket.model.TicketProcedure;
import vn.com.itechcorp.module.ticket.model.TicketStatus;
import vn.com.itechcorp.module.ticket.repository.TicketProcedureRepository;
import vn.com.itechcorp.module.ticket.repository.TicketRepository;
import vn.com.itechcorp.util.StringUtil;

import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;

@SuppressWarnings("Duplicates")
@Service("ticketService")
@Transactional
public class TicketServiceImpl extends AuditableDtoJpaServiceImpl<TicketDTOGet, Ticket, Long> implements TicketService {

    @Autowired
    private TicketRepository ticketRepository;

    @Autowired
    private TicketProcedureRepository ticketProcedureRepository;

    @Autowired
    private TicketCounterService ticketCounterService;

    @Autowired
    private ModalityService modalityService;

    @Autowired
    private ProcedureService procedureService;

    @Override
    public TicketRepository getRepository() {
        return ticketRepository;
    }

    @Override
    public TicketDTOGet convert(Ticket ticket) {
        return new TicketDTOGet(ticket);
    }

    private void validate(TicketDTOCreate obj) throws APIException {
        if (obj.getServices() == null || obj.getServices().isEmpty())
            throw new InvalidOperationOnObjectException("Services should not be empty");

        // Validate service existence
        {
            Set<Long> queryServiceIDs = obj.getServices().stream().map(TicketProcedureDTOCreate::getServiceID).collect(Collectors.toSet());
            List<TicketProcedure> existingServices = ticketProcedureRepository.getTicketProceduresByServiceIDIn(queryServiceIDs);
            Set<Long> existingServiceIDs = existingServices.stream().map(TicketProcedure::getServiceID).collect(Collectors.toSet());
            // Remove any Service which is already existed

            obj.getServices().removeIf(service -> existingServiceIDs.contains(service.getServiceID()));
            if (obj.getServices().isEmpty())
                throw new InvalidOperationOnObjectException("Services after validation should not be empty");
        }

        {
            Set<Long> procedureIDs = obj.getServices().stream().map(TicketProcedureDTOCreate::getProcedureID).collect(Collectors.toSet());
            Map<Long, ProcedureDTOGet> procedures = procedureService.getAllById(procedureIDs).stream().collect(Collectors.toMap(ProcedureDTOGet::getId, p -> p));

            ModalityDTOGet modality = modalityService.getById(obj.getModalityID());
            if (modality.getEnabled() == null || !modality.getEnabled())
                throw new InvalidOperationOnObjectException("Modality {} is not existed or disabled", obj.getModalityID());

            // Validate procedures according to chosen modality
            Set<String> modalityTypes = new HashSet<>();
            modalityTypes.add(modality.getModalityType());
            if (modality.getOtherModalityTypes() != null)
                modalityTypes.addAll(Arrays.asList(modality.getOtherModalityTypes().split(",")));

            obj.getServices().removeIf(service -> {
                ProcedureDTOGet procedure = procedures.get(service.getProcedureID());
                if (procedure == null || !modalityTypes.contains(procedure.getModalityType())) return true;
                return false;
            });

            if (obj.getServices().isEmpty())
                throw new InvalidOperationOnObjectException("Services after validation should not be empty");
        }
    }

    @Override
    public synchronized TicketDTOGet create(BaseDtoCreate<Ticket, Long> entity) throws APIException {
        TicketDTOCreate obj = (TicketDTOCreate) entity;
        validate(obj);

        Ticket entry = entity.toEntry();
        String modalityType = modalityService.getById(entry.getModalityID()).getModalityType();
        String nextTicketNumber = ticketCounterService.getCurrentCounterNumber(modalityType).toString();
        entry.setTicketNumber(StringUtil.paddingToZeros(nextTicketNumber, 3));
        Ticket ticket = ticketRepository.save(entry);

        obj.getServices().forEach(service -> {
            TicketProcedure tp = service.toEntry();
            tp.setTicket(ticket);
            ticketProcedureRepository.save(tp);
        });

        TicketDTOGet result = convert(ticket);
        result.setModality(modalityService.getById(ticket.getModalityID()));
        return result;
    }

    private void fetchLazyInformation(List<TicketDTOGet> tickets) {
        if (tickets == null && tickets.isEmpty()) return;

        List<Long> ticketIDs = tickets.stream().map(TicketDTOGet::getId).collect(Collectors.toList());
        List<Ticket> fullTickets = ticketRepository.findAllById(ticketIDs);
        Map<Long, Ticket> ticketMap = fullTickets.stream().collect(Collectors.toMap(Ticket::getId, Function.identity()));
        for (TicketDTOGet ti : tickets) {
            Ticket ticket = ticketMap.get(ti.getId());
            if (ticket != null && ticket.getTicketProcedures() != null) {
                ti.setTicketProcedures(ticket.getTicketProcedures().stream().map(TicketProcedureDTOGet::new).collect(Collectors.toList()));
            }
        }

        List<Long> modalityIDs = tickets.stream().map(TicketDTOGet::getModalityID).collect(Collectors.toList());
        Map<Long, ModalityDTOGet> modalities = modalityService.getById(modalityIDs).stream().
                collect(Collectors.toMap(ModalityDTOGet::getId, Function.identity()));
        for (TicketDTOGet ti : tickets) {
            ti.setModality(modalities.get(ti.getModalityID()));
        }
    }

    @Override
    public PageOfData<TicketDTOGet> getPageOfData(BaseFilter<Ticket> filter, PaginationInfo pagingInfo) throws APIException {
        PageOfData<TicketDTOGet> pageOfData = super.getPageOfData(filter, pagingInfo);
        fetchLazyInformation(pageOfData.getElements());
        return pageOfData;
    }

    @Override
    public PageOfData<TicketDTOGet> getPageOfData(PaginationInfo pagingInfo) throws APIException {
        PageOfData<TicketDTOGet> pageOfData = super.getPageOfData(pagingInfo);
        fetchLazyInformation(pageOfData.getElements());
        return pageOfData;
    }

    @Override
    public List<TicketSummaryDto> getTicketSummary(TicketSummaryFilter filter){
        List<TicketSummaryDto> dtos = new ArrayList<>();

        // get all modalities in siteID
        List<ModalityDTOGet> modalities = modalityService.getBySiteID(filter.getSiteID());

        if (modalities == null || modalities.isEmpty()) return dtos;

        List<Long> modalityIDs = modalities.stream().map(ModalityDTOGet::getId).collect(Collectors.toList());
        Map<Long, List<Ticket>> modalityTicketMap = modalityIDs.stream().collect(Collectors.toMap(id -> id, id -> new ArrayList<>()));
        // get only started ticket
        List<Ticket> tickets = ticketRepository.findAllByModalityIDInAndStatusNotIn(modalityIDs, Arrays.asList(TicketStatus.COMPLETED, TicketStatus.PASSING));

        // add ticket to respective modality
        for (Ticket t : tickets) {
            List<Ticket> ticketInModality = modalityTicketMap.get(t.getModalityID());
            if(ticketInModality != null){
                ticketInModality.add(t);
            }
        }

        for (ModalityDTOGet modality: modalities) {
            TicketSummaryDto ticketSummaryDto = new TicketSummaryDto();
            ticketSummaryDto.setModalityID(modality.getId());
            ticketSummaryDto.setRoomName(modality.getRoomName());
            ticketSummaryDto.setModalityType(modality.getModalityType());

            // tickets in current modality
            List<Ticket> ticketsInModality = modalityTicketMap.get(modality.getId());
            if (ticketsInModality != null && !ticketsInModality.isEmpty()) {
                // sort ticket by id
                ticketsInModality.sort(Comparator.comparingInt(t -> t.getId().intValue()));

                ticketSummaryDto.setFirstNumber(ticketsInModality.get(0).getTicketNumber());
                if (ticketsInModality.size() > 1) {
                    ticketSummaryDto.setSecondNumber(ticketsInModality.get(1).getTicketNumber());
                }
            }

            dtos.add(ticketSummaryDto);
        }

        return dtos;
    }

}

