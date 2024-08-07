package vn.com.itechcorp.module.procedure.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import vn.com.itechcorp.base.service.dto.Dto;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ProcedureSiteFilter extends Dto {

    @NotNull
    @NotEmpty
    private List<Long> ids;

    @NotNull
    private Long siteID;

}
