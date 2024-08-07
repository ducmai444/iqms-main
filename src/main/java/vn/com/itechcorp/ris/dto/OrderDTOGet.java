package vn.com.itechcorp.ris.dto;

import lombok.Getter;
import lombok.Setter;
import vn.com.itechcorp.base.service.dto.Dto;

import java.util.Date;
import java.util.List;

@Getter @Setter
public class OrderDTOGet extends Dto {

    private Long id;

    private Long studyID;

    private PatientDTO patient;

    private String modalityType;

    private String accessionNumber;

    private Date orderDate;

    private String procedureStepStatus;

    private DiagnosisStepStatus diagnosisStepStatus;

    private StudyDTOGet study;

    private List<OrderProcedureDTOGet> services;

}
