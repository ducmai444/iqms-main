package vn.com.itechcorp.module.procedure.controller;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import vn.com.itechcorp.base.api.response.APIResponse;
import vn.com.itechcorp.module.modality.dto.ModalityDTOGet;
import vn.com.itechcorp.module.procedure.dto.ProcedureSiteFilter;

import javax.validation.Valid;
import java.util.Map;
import java.util.concurrent.CompletableFuture;

@RestController
@Api(value = "procedure-api", tags = "procedure-api")
@RequestMapping("/qms/ws/rest/v1")
public class ProcedureController {

    @Autowired
    private ProcedureMethod method;

    @ApiOperation(value = "Get list of supported procedures")
    @PostMapping("/async/procedure/suggest-modality")
    public CompletableFuture<ResponseEntity<APIResponse<Map<Long, ModalityDTOGet>>>> getSupportedModalities(
            @Valid @RequestBody ProcedureSiteFilter filter) {
        return method.getSupportedModalitiesAsync(filter);
    }

}