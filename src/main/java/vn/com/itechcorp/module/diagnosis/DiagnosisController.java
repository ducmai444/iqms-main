package vn.com.itechcorp.module.diagnosis;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import vn.com.itechcorp.base.api.response.APIListResponse;
import vn.com.itechcorp.module.mwl.dto.MwlDTO;

import java.util.List;
import java.util.concurrent.CompletableFuture;

@RestController
@Api(value = "diagnosis-api", tags = "diagnosis-api")
@RequestMapping("/qms/ws/rest/v1")
public class DiagnosisController {

    @Autowired
    private DiagnosisMethod method;

    @ApiOperation(value = "View a list of filtered worklist")
    @GetMapping("/async/approved-diagnosis")
    public CompletableFuture<ResponseEntity<APIListResponse<List<MwlDTO>>>> getApprovedDiagnosis() {
        return method.getApprovedDiagnosisAsync();
    }

}