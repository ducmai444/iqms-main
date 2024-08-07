package vn.com.itechcorp.security;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

@Getter @Setter
@AllArgsConstructor
public class JwtTokenResponse implements Serializable {

    private static final long serialVersionUID = -8091879091924046844L;

    private String accessToken;
}
