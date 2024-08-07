package vn.com.itechcorp.ris.dto;

import lombok.Getter;
import lombok.Setter;
import vn.com.itechcorp.base.service.dto.Dto;

@Getter @Setter
public class PatientDTO extends Dto {

    private String id;

    private String initialSecret;

    private String pid;

    private String fullname;

    private String gender;

    private String birthDate;

    private String phone;

    private String email;

    private String address;

}
