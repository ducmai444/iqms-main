package vn.com.itechcorp.ris.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;
import java.util.Date;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class UserTokenDTOGet implements Serializable {

    private JwtTokenResponse token;

    private Date expiredDate;

}
