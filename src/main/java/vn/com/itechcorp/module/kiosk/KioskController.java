package vn.com.itechcorp.module.kiosk;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;
import vn.com.itechcorp.base.api.response.APIListResponse;
import vn.com.itechcorp.base.api.response.APIResponse;
import vn.com.itechcorp.base.service.filter.PaginationInfo;
import vn.com.itechcorp.module.modality.controller.ModalityMethod;
import vn.com.itechcorp.module.modality.dto.ModalityDTOGet;
import vn.com.itechcorp.module.modality.dto.ModalityFilter;
import vn.com.itechcorp.module.modality.dto.ModalityTypeFilter;
import vn.com.itechcorp.module.mwl.controller.MwlMethod;
import vn.com.itechcorp.module.mwl.dto.MwlDTO;
import vn.com.itechcorp.module.mwl.dto.MwlFilter;
import vn.com.itechcorp.module.procedure.controller.ProcedureMethod;
import vn.com.itechcorp.module.procedure.dto.ProcedureSiteFilter;
import vn.com.itechcorp.module.ticket.controller.TicketMethod;
import vn.com.itechcorp.module.ticket.dto.TicketDTOCreate;
import vn.com.itechcorp.module.ticket.dto.TicketDTOGet;

import javax.validation.Valid;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.concurrent.CompletableFuture;

@Slf4j
@RestController
@Api(value = "kiosk-api", tags = "kiosk-api")
@RequestMapping("/kiosk/ws/rest/v1")
public class KioskController {

    private final MwlMethod mwlMethod;

    private final ModalityMethod modalityMethod;

    private final ProcedureMethod procedureMethod;

    private final TicketMethod ticketMethod;

    @Autowired
    public KioskController(MwlMethod mwlMethod,
                           ModalityMethod modalityMethod,
                           ProcedureMethod procedureMethod,
                           TicketMethod ticketMethod) {
        this.mwlMethod = mwlMethod;
        this.modalityMethod = modalityMethod;
        this.procedureMethod = procedureMethod;
        this.ticketMethod = ticketMethod;
    }

    @ApiOperation(value = "View a list of filtered worklist")
    @PostMapping("/async/search/mwl")
    public CompletableFuture<ResponseEntity<APIListResponse<List<MwlDTO>>>> search(@Valid @RequestBody MwlFilter filter) {
        log.info("Client KIOSK searched worklist with filter: {} ", filter);
        return mwlMethod.searchAsync(filter);
    }

    @ApiOperation(value = "Get list of supported procedures")
    @PostMapping("/async/procedure/suggest-modality")
    public CompletableFuture<ResponseEntity<APIResponse<Map<Long, ModalityDTOGet>>>> getSupportedModalities(
            @Valid @RequestBody ProcedureSiteFilter filter) {
        log.info("Client KIOSK called api method getSupportedModalities with filter: {}", filter);
        return procedureMethod.getSupportedModalitiesAsync(filter);
    }

    @ApiOperation(value = "View a list of filtered modality")
    @PostMapping("/async/search/modality")
    public CompletableFuture<ResponseEntity<APIListResponse<List<ModalityDTOGet>>>> getAllModalityByFilter(
            @Valid @RequestBody ModalityFilter filter,
            @RequestParam(required = false, name = "orderBy") String orderBy,
            @RequestParam(required = false, name = "offset", defaultValue = "0") int offset,
            @RequestParam(required = false, name = "limit", defaultValue = "100") int limit) {
        log.info("Client KIOSK called api method getAllModalityByFilter with filter: {}", filter);
        return modalityMethod.searchAsync(filter, new PaginationInfo(offset, limit, orderBy));
    }

    @ApiOperation(value = "View a list of all modality types in a hospital")
    @PostMapping("/async/search/modalityType")
    public CompletableFuture<ResponseEntity<APIListResponse<Collection<String>>>> searchModalityTypes(
            Authentication authentication,
            @Valid @RequestBody ModalityTypeFilter filter) {
        log.info("Client KIOSK called api method searchModalityTypes with filter: {}", filter);

        log.info("{}", authentication);
        return modalityMethod.searchModalityTypesAsync(filter);
    }

    @ApiOperation(value = "Create new ticket")
    @PostMapping("/async/ticket")
    public CompletableFuture<ResponseEntity<APIResponse<TicketDTOGet>>> createTicket(@RequestBody @Valid TicketDTOCreate object) {
        log.info("Client KIOSK created new ticket: {}", object);
        return ticketMethod.createAsync(object);
    }

}