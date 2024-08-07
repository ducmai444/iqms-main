package vn.com.itechcorp.module.admin;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Service;
import vn.com.itechcorp.base.api.response.APIResponse;
import vn.com.itechcorp.base.api.response.APIResponseHeader;
import vn.com.itechcorp.base.api.response.APIResponseStatus;
//import vn.com.itechcorp.module.auth.UserService;
import vn.com.itechcorp.ris.proxy.AuthProxy;
import vn.com.itechcorp.security.JwtTokenRequest;
import vn.com.itechcorp.security.JwtTokenResponse;
import vn.com.itechcorp.security.JwtTokenUtil;

@Service
public class AdminMethod {

    @Autowired
    private AuthProxy authProxy;

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private UserDetailsService userDetailsService;

    @Autowired
    private JwtTokenUtil jwtTokenUtil;

    public ResponseEntity<APIResponse<JwtTokenResponse>> login(JwtTokenRequest jwtTokenRequest){
        String username = jwtTokenRequest.getUsername();
        String password = jwtTokenRequest.getPassword();
        try {
            authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(username, password)
            );

            UserDetails userDetails = userDetailsService.loadUserByUsername(username);
            JwtTokenResponse jwtTokenResponse = jwtTokenUtil.generateToken(userDetails);

            return new ResponseEntity<>(new APIResponse<>(new APIResponseHeader(APIResponseStatus.OK, "Logged in"),jwtTokenResponse), HttpStatus.OK);
        } catch (AuthenticationException e) {
            return new ResponseEntity<>(new APIResponse<>(new APIResponseHeader(APIResponseStatus.UNAUTHORIZED, e.getMessage()), null), HttpStatus.UNAUTHORIZED);
        } catch (Exception e){
            return new ResponseEntity<>(new APIResponse<>(new APIResponseHeader(APIResponseStatus.INTERNAL_SERVER_ERROR, e.getMessage()), null), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

}
