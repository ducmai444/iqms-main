package vn.com.itechcorp.ris.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class JwtTokenResponse {

    private String access_token;

    private String token_type;

    private long expires_in;

}
