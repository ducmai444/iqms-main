package vn.com.itechcorp.module.mwl.controller;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import vn.com.itechcorp.base.api.response.APIListResponse;
import vn.com.itechcorp.module.mwl.dto.MwlDTO;
import vn.com.itechcorp.module.mwl.dto.MwlFilter;

import javax.validation.Valid;
import java.util.List;
import java.util.concurrent.CompletableFuture;

@RestController
@Api(value = "mwl-api", tags = "mwl-api")
@RequestMapping("/qms/ws/rest/v1")
public class MwlController {

    @Autowired
    private MwlMethod method;

    @ApiOperation(value = "View a list of filtered worklist")
    @PostMapping("/async/search/mwl")
    public CompletableFuture<ResponseEntity<APIListResponse<List<MwlDTO>>>> search(@Valid @RequestBody MwlFilter filter) {
        return method.searchAsync(filter);
    }

}