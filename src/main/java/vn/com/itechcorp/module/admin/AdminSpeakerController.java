package vn.com.itechcorp.module.admin;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import vn.com.itechcorp.base.api.response.APIListResponse;
import vn.com.itechcorp.base.api.response.APIResponse;
import vn.com.itechcorp.base.service.filter.PaginationInfo;
import vn.com.itechcorp.module.speaker.controller.SpeakerMethod;
import vn.com.itechcorp.module.speaker.dto.SpeakerDtoCreate;
import vn.com.itechcorp.module.speaker.dto.SpeakerDtoGet;

import javax.validation.Valid;
import java.util.List;
import java.util.concurrent.CompletableFuture;

@RestController
@Api(value = "admin-api", tags = "admin-api")
@RequestMapping("/admin/ws/rest/v1")
public class AdminSpeakerController {

    @Autowired
    private SpeakerMethod speakerMethod;

    @ApiOperation(value = "Get all speakers")
    @GetMapping("/speaker")
    public CompletableFuture<ResponseEntity<APIListResponse<List<SpeakerDtoGet>>>> getAllSpeakers(
            @RequestParam(required = false, name = "orderBy") String orderBy,
            @RequestParam(required = false, name = "offset", defaultValue = "0") int offset,
            @RequestParam(required = false, name = "limit", defaultValue = "100") int limit
    ) {
        return speakerMethod.getListAsync(new PaginationInfo(offset, limit, orderBy));
    }

    @ApiOperation(value = "Create new speaker")
    @PostMapping("/speaker")
    public CompletableFuture<ResponseEntity<APIResponse<SpeakerDtoGet>>> createSpeaker(
            @RequestBody @Valid SpeakerDtoCreate speakerDtoCreate
    ) {
        return speakerMethod.createAsync(speakerDtoCreate);
    }

    @PostMapping("/{speakerID}")
    public CompletableFuture<ResponseEntity<Object>> deleteSpeaker(
            @PathVariable Long speakerID
    ) {
        return speakerMethod.deleteAsync(speakerID);
    }
}
