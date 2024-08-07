package vn.com.itechcorp.module.speaker.controller;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import vn.com.itechcorp.base.api.response.APIListResponse;
import vn.com.itechcorp.base.api.response.APIResponse;
import vn.com.itechcorp.base.service.filter.PaginationInfo;
import vn.com.itechcorp.module.speaker.dto.SoundDtoCreate;
import vn.com.itechcorp.module.speaker.dto.SoundDtoGet;
import vn.com.itechcorp.module.speaker.dto.SoundFilter;
import vn.com.itechcorp.module.speaker.dto.SpeakerDtoGet;

import java.util.List;
import java.util.concurrent.CompletableFuture;

@RestController
@Api(value = "speaker-api", tags = "speaker-api")
@RequestMapping("/qms/ws/rest/v1")
public class SoundController {

    @Autowired
    private SoundMethod soundMethod;

    @Autowired
    private SpeakerMethod speakerMethod;

    @ApiOperation(value = "View a list of all speakers")
    @GetMapping("/async/speaker")
    public CompletableFuture<ResponseEntity<APIListResponse<List<SpeakerDtoGet>>>> getSpeakers(
            @RequestParam(required = false, name = "orderBy") String orderBy,
            @RequestParam(required = false, name = "offset", defaultValue = "0") int offset,
            @RequestParam(required = false, name = "limit", defaultValue = "100") int limit) {
        return speakerMethod.getListAsync(new PaginationInfo(offset, limit, orderBy));
    }

    @ApiOperation(value = "Search playlist by speakerID")
    @PostMapping("/search/sound")
    public CompletableFuture<ResponseEntity<APIListResponse<List<SoundDtoGet>>>> searchSoundBySpeaker(
            @RequestBody SoundFilter filter,
            @RequestParam(required = false, name = "orderBy") String orderBy,
            @RequestParam(required = false, name = "offset", defaultValue = "0") int offset,
            @RequestParam(required = false, name = "limit", defaultValue = "100") int limit) {
        return soundMethod.searchAsync(filter, new PaginationInfo(offset, limit, orderBy));
    }

    @ApiOperation(value = "Create sound")
    @PostMapping("/sound")
    public CompletableFuture<ResponseEntity<APIResponse<SoundDtoGet>>> createSound(@RequestBody SoundDtoCreate object) {
        return soundMethod.createAsync(object);
    }

}
