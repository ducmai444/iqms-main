package vn.com.itechcorp.module.modality.controller;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;
import vn.com.itechcorp.base.api.response.APIListResponse;
import vn.com.itechcorp.base.api.response.APIResponse;
import vn.com.itechcorp.base.service.filter.PaginationInfo;
import vn.com.itechcorp.module.modality.dto.*;
import vn.com.itechcorp.module.modality.repository.ModalityRepository;
import vn.com.itechcorp.ris.dto.DiagnosisStepStatus;
import vn.com.itechcorp.ris.proxy.RisProxy;

import javax.validation.Valid;
import java.util.Collection;
import java.util.Date;
import java.util.List;
import java.util.concurrent.CompletableFuture;

@RestController
@Api(value = "modality-api", tags = "modality-api")
@RequestMapping("/qms/ws/rest/v1")
public class ModalityController {

    private static final Logger log = LoggerFactory.getLogger(ModalityController.class);

    @Autowired
    private ModalityMethod method;

    @ApiOperation(value = "View a list of all modality types in a hospital")
    @GetMapping("/async/modalityType")
    public CompletableFuture<ResponseEntity<APIListResponse<List<String>>>> getAllModalityTypes(
            @RequestParam(required = false, name = "orderBy") String orderBy,
            @RequestParam(required = false, name = "offset", defaultValue = "0") int offset,
            @RequestParam(required = false, name = "limit", defaultValue = "100") int limit) {
        return method.getModalityTypesAsync();
    }

    @ApiOperation(value = "View a list of all modality types in a hospital")
    @PostMapping("/async/search/modalityType")
    public CompletableFuture<ResponseEntity<APIListResponse<Collection<String>>>> searchModalityTypes(
            @Valid @RequestBody ModalityTypeFilter filter) {
        return method.searchModalityTypesAsync(filter);
    }

    @ApiOperation(value = "View a list of all modalities")
    @GetMapping("/async/modality")
    public CompletableFuture<ResponseEntity<APIListResponse<List<ModalityDTOGet>>>> getAllModalitiesByHospital(
            @RequestParam(required = false, name = "orderBy") String orderBy,
            @RequestParam(required = false, name = "offset", defaultValue = "0") int offset,
            @RequestParam(required = false, name = "limit", defaultValue = "100") int limit) {
        return method.getListAsync(new PaginationInfo(offset, limit, orderBy));
    }

    @ApiOperation(value = "View a list of filtered modality")
    @PostMapping("/async/search/modality")
    public CompletableFuture<ResponseEntity<APIListResponse<List<ModalityDTOGet>>>> getAllModalityByFilter(
            @Valid @RequestBody ModalityFilter filter,
            @RequestParam(required = false, name = "orderBy") String orderBy,
            @RequestParam(required = false, name = "offset", defaultValue = "0") int offset,
            @RequestParam(required = false, name = "limit", defaultValue = "100") int limit) {
        return method.searchAsync(filter, new PaginationInfo(offset, limit, orderBy));
    }

}