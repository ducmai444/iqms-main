package vn.com.itechcorp.module.auth;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import vn.com.itechcorp.base.api.response.APIListResponse;
import vn.com.itechcorp.ris.dto.BasicAuth;
import vn.com.itechcorp.ris.dto.OrderDTOGet;
import vn.com.itechcorp.ris.dto.UserTokenDTOGet;
import vn.com.itechcorp.ris.proxy.AuthProxy;
import vn.com.itechcorp.ris.proxy.RisProxy;
import vn.com.itechcorp.security.JwtTokenRequest;
import vn.com.itechcorp.security.JwtTokenResponse;
import vn.com.itechcorp.security.JwtTokenUtil;

import javax.validation.Valid;
import java.util.Collection;
import java.util.List;

@Controller
@RequestMapping("/api/ws/auth")
public class AuthController {

    @Autowired
    private AuthProxy authProxy;

    @Autowired
    private RisProxy risProxy;

    private final AuthenticationManager authenticationManager;
    private final JwtTokenUtil jwtTokenUtil;

    @Autowired
    private InMemoryUserDetailsManager inMemoryUserDetailsManager;


    public AuthController(AuthenticationManager authenticationManager, JwtTokenUtil jwtTokenUtil) {
        this.authenticationManager = authenticationManager;
        this.jwtTokenUtil = jwtTokenUtil;
    }

    @Autowired
    private PasswordEncoder passwordEncoder;

    @PostMapping(value = "/login")
    public ResponseEntity<String> authenticateUser(@Valid @RequestBody JwtTokenRequest jwtTokenRequest) {

        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(jwtTokenRequest.getUsername(),
                        jwtTokenRequest.getPassword())
        );

        SecurityContextHolder.getContext().setAuthentication(authentication);

        BasicAuth auth = new BasicAuth(jwtTokenRequest.getUsername(), jwtTokenRequest.getPassword());
        UserTokenDTOGet userTokenDTOGet = authProxy.getToken(auth);

        return ResponseEntity.ok(userTokenDTOGet.getToken().getAccess_token());
    }


}
