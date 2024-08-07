package vn.com.itechcorp.security;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import java.io.Serializable;
import java.util.*;

@Component
public class JwtTokenUtil implements Serializable {

    private static final long serialVersionUID = -2550185165626007488L;

    public static final long JWT_TOKEN_VALIDITY = 12 * 60 * 60;

    private final String SECRET = "secret";

    public Claims getClaimsWithoutSignatureVerification(String token) {
        // Tách phần payload từ token
        String[] parts = token.split("\\.");
        if (parts.length != 3) {
            throw new IllegalArgumentException("Token không hợp lệ");
        }

        // Giải mã phần payload
        String payload = new String(Base64.getUrlDecoder().decode(parts[1]));

        // Trả về Claims từ payload đã giải mã
        Claims claims = Jwts.parser().parseClaimsJwt(parts[0] + "." + parts[1] + ".").getBody();
        return claims;
    }

    public String getUsernameFromToken(String token) {
        return getClaimsWithoutSignatureVerification(token).get("username", String.class);
    }

    public Date getExpirationDateFromToken(String token) {
        return getClaimsWithoutSignatureVerification(token).getExpiration();
    }

    //generate token
    public JwtTokenResponse generateToken(UserDetails userDetails) {
        Date now = new Date(System.currentTimeMillis());
        Date expiryDate = new Date(now.getTime() + JWT_TOKEN_VALIDITY * 1000);

        String token = Jwts.builder()
                .setSubject(userDetails.getUsername())
                .setIssuedAt(now)
                .setExpiration(expiryDate)
                .signWith(SignatureAlgorithm.HS512, SECRET)
                .compact();

        return new JwtTokenResponse(token);
    }

    public boolean isTokenExpired(String token){
        Date expire = getClaimsWithoutSignatureVerification(token).getExpiration();;
        return expire.before(new Date());
    }

    //validate token
    public Boolean validateToken(String token, UserDetails userDetails) {
        final String username = getUsernameFromToken(token);
        return (username.equals(userDetails.getUsername()) && !isTokenExpired(token));
    }
}
