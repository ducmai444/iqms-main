package vn.com.itechcorp.module.admin;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;
import vn.com.itechcorp.base.api.response.APIListResponse;
import vn.com.itechcorp.base.api.response.APIResponse;
import vn.com.itechcorp.base.service.filter.PaginationInfo;
import vn.com.itechcorp.module.modality.controller.ModalityMethod;
import vn.com.itechcorp.module.modality.dto.ModalityDTOGet;
import vn.com.itechcorp.module.modality.dto.ModalityDTOUpdate;
import vn.com.itechcorp.module.modality.dto.ModalityDtoCreate;
import vn.com.itechcorp.module.modality.dto.ModalityFilter;

import javax.validation.Valid;
import java.util.Date;
import java.util.List;
import java.util.concurrent.CompletableFuture;

@RestController
@Api(value = "admin-api", tags = "admin-api")
@RequestMapping("/admin/ws/rest/v1")
public class AdminModalityController {

    @Autowired
    private ModalityMethod modalityMethod;

    @GetMapping("/modality")
    public CompletableFuture<ResponseEntity<APIListResponse<List<ModalityDTOGet>>>> getAllModalities(
            @RequestParam(required = false, name = "orderBy") String orderBy,
            @RequestParam(required = false, name = "offset", defaultValue = "0") int offset,
            @RequestParam(required = false, name = "limit", defaultValue = "100") int limit
    ) {
        return modalityMethod.getListAsync(new PaginationInfo(offset, limit, orderBy));
    }

    @PostMapping("/search/modality")
    public CompletableFuture<ResponseEntity<APIListResponse<List<ModalityDTOGet>>>> getListOfModalities(
            @Valid @RequestBody ModalityFilter filter,
            @RequestParam(required = false, name = "orderBy") String orderBy,
            @RequestParam(required = false, name = "offset", defaultValue = "0") int offset,
            @RequestParam(required = false, name = "limit", defaultValue = "100") int limit
    ) {
        return modalityMethod.searchAsync(filter, new PaginationInfo(offset, limit, orderBy));
    }

    @ApiOperation(value = "Create new modality")
    @PostMapping("/modality")
    public CompletableFuture<ResponseEntity<APIResponse<ModalityDTOGet>>> createModality(
            @RequestBody @Valid ModalityDtoCreate modalityDtoCreate
    ) {
        String username = SecurityContextHolder.getContext().getAuthentication().getName();
        Date now = new Date(System.currentTimeMillis());

        modalityDtoCreate.setCreatorName(username);
        modalityDtoCreate.setCreatedTime(now);

        modalityMethod.create(modalityDtoCreate);

        return modalityMethod.createAsync(modalityDtoCreate);
    }

    @PutMapping("/modality")
    public CompletableFuture<ResponseEntity<APIResponse<ModalityDTOGet>>> updateModality(
            @RequestBody ModalityDTOUpdate modalityDTOUpdate
    ) {
        String username = SecurityContextHolder.getContext().getAuthentication().getName();
        Date now = new Date(System.currentTimeMillis());

        modalityDTOUpdate.setLastUpdatedBy(username);
        modalityDTOUpdate.setLastUpdatedTime(now);

        return modalityMethod.updateAsync(modalityDTOUpdate);
    }

    @DeleteMapping("/{modalityID}")
    public CompletableFuture<ResponseEntity<Object>> deleteModality(
            @PathVariable Long modalityID
    ) {
        return modalityMethod.deleteAsync(modalityID);
    }
}
