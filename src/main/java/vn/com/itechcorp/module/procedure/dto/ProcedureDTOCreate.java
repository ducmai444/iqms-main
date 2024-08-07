package vn.com.itechcorp.module.procedure.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import vn.com.itechcorp.base.service.dto.BaseDtoCreate;
import vn.com.itechcorp.module.procedure.model.Procedure;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.util.List;

@Getter @Setter
@AllArgsConstructor
public class ProcedureDTOCreate extends BaseDtoCreate<Procedure, Long> {

    @NotNull @NotEmpty
    private String name;

    @NotNull @NotEmpty
    private String code;

    private String modalityType;

    private List<Long> modalityIDs;

    @Override
    public Procedure toEntry() {
        Procedure procedure = new Procedure();
        procedure.setName(name);
        procedure.setCode(code);
        procedure.setModalityType(modalityType);

        return procedure;
    }

    @JsonIgnore
    @Override
    public Long getId() {
        return null;
    }
}
