package vn.com.itechcorp.module.mwl.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import vn.com.itechcorp.base.service.filter.SpecBuilder;
import vn.com.itechcorp.module.modality.model.Modality;
import vn.com.itechcorp.module.modality.repository.ModalityRepository;
import vn.com.itechcorp.module.mwl.dto.MwlDTO;
import vn.com.itechcorp.module.mwl.dto.MwlFilter;
import vn.com.itechcorp.module.ticket.model.Ticket;
import vn.com.itechcorp.module.ticket.dto.TicketFilter;
import vn.com.itechcorp.module.ticket.model.TicketProcedure;
import vn.com.itechcorp.module.ticket.repository.TicketRepository;
import vn.com.itechcorp.ris.dto.OrderDTOGet;
import vn.com.itechcorp.ris.dto.OrderFilter;
import vn.com.itechcorp.ris.dto.OrderProcedureDTOGet;
import vn.com.itechcorp.ris.service.RisService;
import vn.com.itechcorp.util.StringUtil;

import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;

@Service
public class MwlServiceImpl implements MwlService {

    @Autowired
    private RisService risService;

    @Autowired
    private TicketRepository ticketRepository;

    @Autowired
    private ModalityRepository modalityRepository;

    public List<MwlDTO> search(MwlFilter filter) {
        List<OrderDTOGet> orders = risService.search(new OrderFilter(filter.getPid(), filter.getFromDate(), filter.getToDate(), null));
        List<Modality> modalities = filter.getSiteID() == null ? modalityRepository.findAll() : modalityRepository.findAllBySiteID(filter.getSiteID());
        Map<Long, Modality> modalityMap = modalities.stream().collect(Collectors.toMap(Modality::getId, Function.identity()));

        Set<String> modalityTypes = new HashSet<>();
        for (Modality modality : modalities) {
            if (modality.getOtherModalityTypes() != null) {
                modalityTypes.addAll(Arrays.asList(modality.getOtherModalityTypes().split(",")));
            }

            modalityTypes.add(modality.getModalityType());
        }

        List<MwlDTO> risMWLs = new ArrayList<>();
        for (OrderDTOGet order : orders) {
            if (!modalityTypes.contains(order.getModalityType())) continue;
            for (OrderProcedureDTOGet service : order.getServices()) {
                MwlDTO mwl = new MwlDTO();
                mwl.setPid(order.getPatient().getPid());
                mwl.setPatientName(order.getPatient().getFullname());
                mwl.setBirthDate(order.getPatient().getBirthDate());
                mwl.setAddress(order.getPatient().getAddress());
                mwl.setGender(order.getPatient().getGender());
                mwl.setOrderID(order.getId());
                mwl.setServiceID(service.getId());
                mwl.setProcedureID(service.getProcedureID());
                mwl.setProcedureCode(service.getRequestedProcedureCode());
                mwl.setProcedureName(service.getRequestedProcedureName());
                mwl.setAccessionNumber(order.getAccessionNumber());
                risMWLs.add(mwl);
            }
        }

        List<Ticket> tickets = ticketRepository.findAll(SpecBuilder.createSpecification(new TicketFilter(filter.getPid(), null, null, null)));
        Map<Long, MwlDTO> existingMWLs = new HashMap<>();
        for (Ticket ticket : tickets) {
            // TODO Hot fix
            if (modalityMap.get(ticket.getModalityID()) == null) continue; // Do not count ticket which are not belonging to the site
            for (TicketProcedure service : ticket.getTicketProcedures()) {
                MwlDTO mwl = new MwlDTO();
                mwl.setModalityID(ticket.getModalityID());
                mwl.setPid(ticket.getPid());
                mwl.setPatientName(ticket.getPatientName());
                mwl.setGender(ticket.getGender());
                mwl.setBirthDate(ticket.getBirthDate());
                mwl.setAddress(ticket.getAddress());
                mwl.setOrderID(service.getOrderID());
                mwl.setServiceID(service.getServiceID());
                mwl.setProcedureID(service.getProcedureID());
                mwl.setProcedureCode(service.getProcedureCode());
                mwl.setProcedureName(service.getProcedureName());
                mwl.setTicketID(ticket.getId());
                mwl.setTicketNumber(ticket.getTicketNumber());
                mwl.setTicketCreatedDate(ticket.getDateCreated());
                mwl.setAccessionNumber(service.getAccessionNumber());
                mwl.setModalityID(ticket.getModalityID());
                mwl.setRoomName(modalityMap.get(ticket.getModalityID()).getRoomName());
                existingMWLs.putIfAbsent(service.getServiceID(), mwl);
            }
        }

        if (filter.getCheckIn() == null) {
            for (MwlDTO mwl : risMWLs) {
                if (existingMWLs.get(mwl.getServiceID()) == null) {
                    existingMWLs.put(mwl.getServiceID(), mwl);
                }
            }
            return new ArrayList<>(existingMWLs.values());
        }

        if (filter.getCheckIn() == true) {
            return new ArrayList<>(existingMWLs.values());
        }

        // else
        return risMWLs.stream().filter(mwl -> existingMWLs.get(mwl.getServiceID()) == null).collect(Collectors.toList());
    }

}
