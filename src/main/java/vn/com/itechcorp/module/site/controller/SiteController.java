package vn.com.itechcorp.module.site.controller;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import vn.com.itechcorp.base.api.response.APIResponse;
import vn.com.itechcorp.module.site.dto.SiteDTOGet;

import java.util.concurrent.CompletableFuture;

@RestController
@Api(value = "site-api", tags = "site-api")
@RequestMapping("/qms/ws/rest/v1")
public class SiteController {

    @Autowired
    private SiteMethod method;

    @ApiOperation(value = "View site information")
    @GetMapping("/async/site/{id}")
    public CompletableFuture<ResponseEntity<APIResponse<SiteDTOGet>>> getSiteByID(@PathVariable Long id) {
        return method.getByIdAsync(id);
    }

}