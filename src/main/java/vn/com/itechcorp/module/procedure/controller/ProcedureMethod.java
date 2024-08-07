package vn.com.itechcorp.module.procedure.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import vn.com.itechcorp.base.api.method.AsyncBaseDtoAPIMethod;
import vn.com.itechcorp.base.api.response.*;
import vn.com.itechcorp.module.modality.dto.ModalityDTOGet;
import vn.com.itechcorp.module.procedure.dto.ProcedureDTOGet;
import vn.com.itechcorp.module.procedure.dto.ProcedureSiteFilter;
import vn.com.itechcorp.module.procedure.model.Procedure;
import vn.com.itechcorp.module.procedure.service.ProcedureService;

import java.util.Map;
import java.util.concurrent.CompletableFuture;

@Service
public class ProcedureMethod extends AsyncBaseDtoAPIMethod<ProcedureDTOGet, Procedure, Long> {

    private final ProcedureService procedureService;

    public ProcedureMethod(ProcedureService service) {
        super(service);
        procedureService = service;
    }

    public ResponseEntity<APIResponse<Map<Long, ModalityDTOGet>>> getSupportedModalities(ProcedureSiteFilter filter) {
        Map<Long, ModalityDTOGet> result = procedureService.getSupportedModalities(filter);
        return new ResponseEntity<>(new APIResponse<>(new APIResponseHeader(APIResponseStatus.FOUND, "Found"), result), HttpStatus.OK);
    }

    @Async("threadPoolExecutor")
    public CompletableFuture<ResponseEntity<APIResponse<Map<Long, ModalityDTOGet>>>> getSupportedModalitiesAsync(ProcedureSiteFilter filter) {
        return CompletableFuture.completedFuture(getSupportedModalities(filter));
    }

}
