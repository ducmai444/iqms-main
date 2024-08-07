package vn.com.itechcorp.module.diagnosis;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import vn.com.itechcorp.base.api.response.APIListResponse;
import vn.com.itechcorp.base.api.response.APIListResponseHeader;
import vn.com.itechcorp.base.api.response.APIResponseStatus;
import vn.com.itechcorp.base.service.filter.SpecBuilder;
import vn.com.itechcorp.module.mwl.dto.MwlDTO;
import vn.com.itechcorp.module.ticket.dto.TicketFilter;
import vn.com.itechcorp.module.ticket.model.Ticket;
import vn.com.itechcorp.module.ticket.model.TicketProcedure;
import vn.com.itechcorp.module.ticket.repository.TicketRepository;
import vn.com.itechcorp.ris.dto.DiagnosisStepStatus;
import vn.com.itechcorp.ris.dto.OrderDTOGet;
import vn.com.itechcorp.ris.service.RisService;
import vn.com.itechcorp.util.StringUtil;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.concurrent.CompletableFuture;
import java.util.stream.Collectors;

@Service
@Transactional
public class DiagnosisMethod {

    @Autowired
    private RisService risService;

    @Autowired
    private TicketRepository ticketRepository;

    public ResponseEntity<APIListResponse<List<MwlDTO>>> getApprovedDiagnosis() {
        List<Ticket> tickets = ticketRepository.findAll(SpecBuilder.createSpecification(new TicketFilter(null, null, null, null)));
        List<MwlDTO> results = new ArrayList<>();

        if (!tickets.isEmpty()) {
            Set<Long> orderIDs = tickets.stream().flatMap(t -> t.getTicketProcedures().stream()).map(TicketProcedure::getOrderID).collect(Collectors.toSet());
            List<OrderDTOGet> orders = risService.getOrders(orderIDs);

            Set<Long> approvedOrderIDs = orders.stream().filter(o -> o.getDiagnosisStepStatus().equals(DiagnosisStepStatus.APPROVED)).map(OrderDTOGet::getId).collect(Collectors.toSet());
            for (Ticket ticket : tickets) {
                for (TicketProcedure service : ticket.getTicketProcedures()) {
                    if (!approvedOrderIDs.contains(service.getOrderID())) continue;

                    MwlDTO mwl = new MwlDTO();
                    mwl.setModalityID(ticket.getModalityID());
                    mwl.setPid(ticket.getPid());
                    mwl.setPatientName(ticket.getPatientName());
                    mwl.setGender(ticket.getGender());
                    mwl.setBirthDate(ticket.getBirthDate());
                    mwl.setAddress(ticket.getAddress());
                    mwl.setOrderID(service.getOrderID());
                    mwl.setServiceID(service.getServiceID());
                    mwl.setProcedureCode(service.getProcedureCode());
                    mwl.setProcedureName(service.getProcedureName());
                    mwl.setTicketID(ticket.getId());
                    mwl.setTicketNumber(ticket.getTicketNumber());
                    mwl.setAccessionNumber(service.getAccessionNumber());
                    results.add(mwl);
                }
            }
        }

        return ResponseEntity.ok(new APIListResponse<>(new APIListResponseHeader(APIResponseStatus.FOUND, results.size() + " record(s) found", 0, results.size(), results.size()), results));
    }

    @Async("threadPoolExecutor")
    public CompletableFuture<ResponseEntity<APIListResponse<List<MwlDTO>>>> getApprovedDiagnosisAsync() {
        return CompletableFuture.completedFuture(getApprovedDiagnosis());
    }


}
