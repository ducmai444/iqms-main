package vn.com.itechcorp.module.mwl.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Getter;
import lombok.Setter;
import vn.com.itechcorp.base.service.dto.Dto;

import java.util.Date;

@Getter
@Setter
public class MwlDTO extends Dto {

    private String pid;

    private String patientName;

    private String birthDate;

    private String gender;

    private String address;

    private Long orderID;

    private Long serviceID;

    private Long procedureID;

    private String procedureCode;

    private String procedureName;

    private Long modalityID;

    private String roomName;

    private Long ticketID;

    private String ticketNumber;

    @JsonFormat(pattern="yyyyMMddHHmmss", timezone="Asia/Bangkok")
    private Date ticketCreatedDate;

    private String accessionNumber;

}
