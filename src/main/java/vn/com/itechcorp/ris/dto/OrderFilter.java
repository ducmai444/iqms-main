package vn.com.itechcorp.ris.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class OrderFilter {

    private String pid;

    private String fromDate;

    private String toDate;

    private DiagnosisStepStatus diagnosisStepStatus;

}
