package vn.com.itechcorp.module.admin;

import io.swagger.annotations.Api;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;
import vn.com.itechcorp.base.api.response.APIResponse;
import vn.com.itechcorp.security.JwtTokenRequest;
import vn.com.itechcorp.security.JwtTokenResponse;

import javax.validation.Valid;

@RestController
@Api(value = "admin-api", tags = "admin-api")
@RequestMapping("/admin/ws/rest/v1")
public class AdminController {

    @Autowired
    private AdminMethod adminMethod;

    @PostMapping("/login")
    public ResponseEntity<APIResponse<JwtTokenResponse>> login(
            @RequestBody @Valid JwtTokenRequest jwtTokenRequest){
        return adminMethod.login(jwtTokenRequest);
    }

}