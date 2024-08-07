package vn.com.itechcorp.module.procedure.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import vn.com.itechcorp.base.service.dto.DtoUpdate;
import vn.com.itechcorp.module.procedure.model.Procedure;

@Getter @Setter
@NoArgsConstructor
@AllArgsConstructor
public class ProcedureDtoUpdate extends DtoUpdate<Procedure, Long> {

    private String name;

    private String code;

    private String modalityType;

    @Override
    public boolean apply(Procedure procedure) {
        boolean modified = false;

        if (name != null && !name.isEmpty()){
            procedure.setName(name);
            modified = true;
        }

        if (code != null && !code.isEmpty()){
            procedure.setCode(code);
            modified = true;
        }

        if (modalityType != null && !modalityType.isEmpty()){
            procedure.setModalityType(modalityType);
            modified = true;
        }

        return modified;
    }
}
