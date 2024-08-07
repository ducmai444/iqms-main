package vn.com.itechcorp.module.procedure.dto;

import lombok.Getter;
import lombok.Setter;
import vn.com.itechcorp.base.service.dto.DtoGet;
import vn.com.itechcorp.module.procedure.model.Procedure;

@Getter
@Setter
public class ProcedureDTOGet extends DtoGet<Procedure, Long> {

    private String name;

    private String code;

    private String modalityType;

    public ProcedureDTOGet(Procedure object) {
        super(object);
    }

    @Override
    public void parse(Procedure object) {
        this.name = object.getName();
        this.code = object.getCode();
        this.modalityType = object.getModalityType();
    }

}
