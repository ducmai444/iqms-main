package vn.com.itechcorp.module.admin;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import vn.com.itechcorp.base.api.response.APIListResponse;
import vn.com.itechcorp.base.api.response.APIResponse;
import vn.com.itechcorp.base.service.filter.PaginationInfo;
import vn.com.itechcorp.module.site.controller.SiteMethod;
import vn.com.itechcorp.module.site.dto.SiteDTOGet;
import vn.com.itechcorp.module.site.dto.SiteDtoCreate;

import javax.validation.Valid;
import java.util.List;
import java.util.concurrent.CompletableFuture;

@RestController
@Api(value = "admin-api", tags = "admin-api")
@RequestMapping("/admin/ws/rest/v1")
public class AdminSiteController {

    @Autowired
    private SiteMethod siteMethod;

    @ApiOperation(value = "Get all sites")
    @GetMapping("/site")
    public CompletableFuture<ResponseEntity<APIListResponse<List<SiteDTOGet>>>> getAllSites(
            @RequestParam(required = false, name = "orderBy") String orderBy,
            @RequestParam(required = false, name = "offset", defaultValue = "0") int offset,
            @RequestParam(required = false, name = "limit", defaultValue = "100") int limit
    ) {
        return siteMethod.getListAsync(new PaginationInfo(offset, limit, orderBy));
    }

    @ApiOperation(value = "Create new site")
    @PostMapping("/site")
    public CompletableFuture<ResponseEntity<APIResponse<SiteDTOGet>>> createSite(
            @RequestBody @Valid SiteDtoCreate siteDtoCreate
    ) {
        return siteMethod.createAsync(siteDtoCreate);
    }

    @PostMapping("/{siteID}")
    public CompletableFuture<ResponseEntity<Object>> deleteSite(
            @PathVariable Long siteID
    ) {
        return siteMethod.deleteAsync(siteID);
    }

}
