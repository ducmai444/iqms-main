package vn.com.itechcorp.module.mwl.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import vn.com.itechcorp.base.api.response.APIListResponse;
import vn.com.itechcorp.base.api.response.APIListResponseHeader;
import vn.com.itechcorp.base.api.response.APIResponseStatus;
import vn.com.itechcorp.module.mwl.dto.MwlDTO;
import vn.com.itechcorp.module.mwl.dto.MwlFilter;
import vn.com.itechcorp.module.mwl.service.MwlService;

import java.util.List;
import java.util.concurrent.CompletableFuture;

@Service
public class MwlMethod {

    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    private final MwlService mwlService;

    public MwlMethod(MwlService service) {
        mwlService = service;
    }

    public ResponseEntity<APIListResponse<List<MwlDTO>>> search(MwlFilter filter) {
        logger.info("[MWL][Search] Filter requested: {}", filter);
        List<MwlDTO> results = mwlService.search(filter);
        if (results != null && !results.isEmpty()) {
            // Logging
            logger.info("[MWL][Search] results: {}", results);
        }

        return ResponseEntity.ok(new APIListResponse<>(new APIListResponseHeader(APIResponseStatus.FOUND, results.size() + " record(s) found", 0, results.size(), results.size()), results));
    }

    @Async("threadPoolExecutor")
    public CompletableFuture<ResponseEntity<APIListResponse<List<MwlDTO>>>> searchAsync(MwlFilter filter) {
        return CompletableFuture.completedFuture(search(filter));
    }


}
