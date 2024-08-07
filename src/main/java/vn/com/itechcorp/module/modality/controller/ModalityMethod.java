package vn.com.itechcorp.module.modality.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import vn.com.itechcorp.base.api.method.AsyncBaseDtoAPIMethod;
import vn.com.itechcorp.base.api.response.APIListResponse;
import vn.com.itechcorp.base.api.response.APIListResponseHeader;
import vn.com.itechcorp.base.api.response.APIResponseStatus;
import vn.com.itechcorp.module.modality.dto.ModalityDTOGet;
import vn.com.itechcorp.module.modality.dto.ModalityTypeFilter;
import vn.com.itechcorp.module.modality.model.Modality;
import vn.com.itechcorp.module.modality.service.ModalityService;

import java.util.Collection;
import java.util.List;
import java.util.concurrent.CompletableFuture;

@Service
public class ModalityMethod extends AsyncBaseDtoAPIMethod<ModalityDTOGet, Modality, Long> {

    private final ModalityService modalityService;

    public ModalityMethod(ModalityService service) {
        super(service);
        modalityService = service;
    }

    public ResponseEntity<APIListResponse<List<String>>> getModalityTypes() {
        List<String> results = modalityService.getModalityTypes();
        return ResponseEntity.ok(new APIListResponse<>(new APIListResponseHeader(APIResponseStatus.FOUND, results.size() + " record(s) found", 0, results.size(), results.size()), results));
    }

    @Async("threadPoolExecutor")
    public CompletableFuture<ResponseEntity<APIListResponse<List<String>>>> getModalityTypesAsync() {
        return CompletableFuture.completedFuture(getModalityTypes());
    }

    public ResponseEntity<APIListResponse<Collection<String>>> searchModalityTypes(ModalityTypeFilter filter) {
        Collection<String> results = modalityService.getModalityTypes(filter);
        return ResponseEntity.ok(new APIListResponse<>(new APIListResponseHeader(APIResponseStatus.FOUND, results.size() + " record(s) found", 0, results.size(), results.size()), results));
    }

    @Async("threadPoolExecutor")
    public CompletableFuture<ResponseEntity<APIListResponse<Collection<String>>>> searchModalityTypesAsync(ModalityTypeFilter filter) {
        return CompletableFuture.completedFuture(searchModalityTypes(filter));
    }

}
