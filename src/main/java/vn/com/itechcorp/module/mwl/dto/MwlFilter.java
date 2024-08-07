package vn.com.itechcorp.module.mwl.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.io.Serializable;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class MwlFilter implements Serializable {

    @NotNull
    @NotEmpty
    private String pid;

    private String fromDate;

    private String toDate;

    private Boolean checkIn;

    private Long siteID;

    @Override
    public String toString() {
        return "MwlFilter {" +
                "\"pid\": " + pid +
                ", \"fromDate\": " + fromDate +
                ", \"toDate\": " + toDate +
                ", \"checkIn\": " + checkIn +
                ", \"siteID\": " + siteID + "}";
    }

}
