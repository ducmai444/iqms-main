package vn.com.itechcorp.module.ticket.controller;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import vn.com.itechcorp.base.api.response.APIListResponse;
import vn.com.itechcorp.base.api.response.APIResponse;
import vn.com.itechcorp.base.service.filter.PaginationInfo;
import vn.com.itechcorp.module.ticket.dto.*;

import javax.validation.Valid;
import java.util.List;
import java.util.concurrent.CompletableFuture;

@RestController
@Api(value = "ticket-api", tags = "ticket-api")
@RequestMapping("/qms/ws/rest/v1")
public class TicketController {

    @Autowired
    private TicketMethod method;

    @ApiOperation(value = "View a list of all tickets")
    @GetMapping("/async/ticket")
    public CompletableFuture<ResponseEntity<APIListResponse<List<TicketDTOGet>>>> getAllTicket(
            @RequestParam(required = false, name = "orderBy") String orderBy,
            @RequestParam(required = false, name = "offset", defaultValue = "0") int offset,
            @RequestParam(required = false, name = "limit", defaultValue = "100") int limit) {
        return method.getListAsync(new PaginationInfo(offset, limit, orderBy));
    }

    @ApiOperation(value = "View a list of all tickets")
    @PostMapping("/async/search/ticket")
    public CompletableFuture<ResponseEntity<APIListResponse<List<TicketDTOGet>>>> getAllTicket(
            @RequestBody TicketFilter filter,
            @RequestParam(required = false, name = "orderBy", defaultValue = "+id") String orderBy,
            @RequestParam(required = false, name = "offset", defaultValue = "0") int offset,
            @RequestParam(required = false, name = "limit", defaultValue = "100") int limit) {
        return method.searchAsync(filter, new PaginationInfo(offset, limit, orderBy));
    }

    @ApiOperation(value = "Create new ticket")
    @PostMapping("/async/ticket")
    public CompletableFuture<ResponseEntity<APIResponse<TicketDTOGet>>> createTicket(@RequestBody @Valid TicketDTOCreate object) {
        return method.createAsync(object);
    }

    @ApiOperation(value = "Update a ticket")
    @PutMapping("/async/ticket")
    public CompletableFuture<ResponseEntity<APIResponse<TicketDTOGet>>> updateTicket(@RequestBody @Valid TicketDTOUpdate object) {
        return method.updateAsync(object);
    }

    @ApiOperation(value = "get ticket summary")
    @PostMapping("/async/search/ticket-summary")
    public ResponseEntity<APIListResponse<List<TicketSummaryDto>>> getTicketSummary(@RequestBody TicketSummaryFilter filter){
        return method.getTicketSummary(filter);
    }

}