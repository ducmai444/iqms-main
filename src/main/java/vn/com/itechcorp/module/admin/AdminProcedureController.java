package vn.com.itechcorp.module.admin;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import vn.com.itechcorp.base.api.response.APIListResponse;
import vn.com.itechcorp.base.api.response.APIResponse;
import vn.com.itechcorp.base.service.filter.PaginationInfo;
import vn.com.itechcorp.module.procedure.controller.ProcedureMethod;
import vn.com.itechcorp.module.procedure.dto.ProcedureDTOCreate;
import vn.com.itechcorp.module.procedure.dto.ProcedureDTOGet;
import vn.com.itechcorp.module.procedure.dto.ProcedureDtoUpdate;

import javax.validation.Valid;
import java.util.List;
import java.util.concurrent.CompletableFuture;

@RestController
@Api(value = "admin-api", tags = "admin-api")
@RequestMapping("/admin/ws/rest/v1")
public class AdminProcedureController {

    @Autowired
    private ProcedureMethod procedureMethod;

    @ApiOperation(value = "Get a list of all procedures")
    @GetMapping("/procedure")
    public CompletableFuture<ResponseEntity<APIListResponse<List<ProcedureDTOGet>>>> getAllProcedures(
            @RequestParam(required = false, name = "orderBy") String orderBy,
            @RequestParam(required = false, name = "offset", defaultValue = "0") int offset,
            @RequestParam(required = false, name = "limit", defaultValue = "100") int limit
    ) {
        return procedureMethod.getListAsync(new PaginationInfo(offset, limit, orderBy));
    }

    @ApiOperation(value = "Create new procedure")
    @PostMapping("/procedure")
    public CompletableFuture<ResponseEntity<APIResponse<ProcedureDTOGet>>> createProcedure(
            @RequestBody @Valid ProcedureDTOCreate procedureDTOCreate
    ) {
        return procedureMethod.createAsync(procedureDTOCreate);
    }

    @PutMapping("/procedure")
    public CompletableFuture<ResponseEntity<APIResponse<ProcedureDTOGet>>> updateProcedure(
            @RequestBody ProcedureDtoUpdate procedureDtoUpdate
    ) {
        return procedureMethod.updateAsync(procedureDtoUpdate);
    }

    @DeleteMapping("/{procedureID}")
    public CompletableFuture<ResponseEntity<Object>> deleteProcedure(
            @PathVariable Long procedureID
    ) {
        return procedureMethod.deleteAsync(procedureID);
    }
}
