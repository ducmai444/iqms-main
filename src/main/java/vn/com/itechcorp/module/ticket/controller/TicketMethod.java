package vn.com.itechcorp.module.ticket.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import vn.com.itechcorp.base.api.method.AsyncBaseDtoAPIMethod;
import vn.com.itechcorp.base.api.response.APIListResponse;
import vn.com.itechcorp.base.api.response.APIListResponseHeader;
import vn.com.itechcorp.base.api.response.APIResponseStatus;
import vn.com.itechcorp.base.service.BaseDtoService;
import vn.com.itechcorp.module.ticket.dto.TicketSummaryDto;
import vn.com.itechcorp.module.ticket.dto.TicketDTOGet;
import vn.com.itechcorp.module.ticket.dto.TicketSummaryFilter;
import vn.com.itechcorp.module.ticket.model.Ticket;
import vn.com.itechcorp.module.ticket.service.TicketService;

import java.util.List;

@Service
public class TicketMethod extends AsyncBaseDtoAPIMethod<TicketDTOGet, Ticket, Long> {

    private final TicketService ticketService;

    public TicketMethod(TicketService service) {
        super(service);
        this.ticketService = service;
    }

    public ResponseEntity<APIListResponse<List<TicketSummaryDto>>> getTicketSummary(TicketSummaryFilter filter){
        List<TicketSummaryDto> result = ticketService.getTicketSummary(filter);
        return ResponseEntity.ok(new APIListResponse<>(new APIListResponseHeader(APIResponseStatus.FOUND, result.size() + " result(s) found!", 0, result.size(), result.size()), result));
    }
}
