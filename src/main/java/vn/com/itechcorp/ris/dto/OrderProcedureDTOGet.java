package vn.com.itechcorp.ris.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import vn.com.itechcorp.base.service.dto.Dto;

@Getter @Setter @NoArgsConstructor
public class OrderProcedureDTOGet extends Dto {

    private Long id;

    private String hospitalID;

    private String orderID;

    private String icdCode;

    private String requestedNumber;

    private String requestedDatetime;

    private Long procedureID;

    private String requestedProcedureCode;

    private String requestedProcedureName;

    private String requestedModalityType;

    private String approvedReportID;

    private String creationType;

    private String approvedDatetime;

}
