package vn.com.itechcorp.module.procedure.service;

import vn.com.itechcorp.base.service.BaseDtoService;
import vn.com.itechcorp.module.modality.dto.ModalityDTOGet;
import vn.com.itechcorp.module.procedure.dto.ProcedureDTOGet;
import vn.com.itechcorp.module.procedure.dto.ProcedureSiteFilter;
import vn.com.itechcorp.module.procedure.model.Procedure;

import java.util.*;

public interface ProcedureService extends BaseDtoService<ProcedureDTOGet, Procedure, Long> {
    /**
     * @param filter
     * @return Map<[ ProcedureID, ModalityID ]>
     */
    Map<Long, ModalityDTOGet> getSupportedModalities(ProcedureSiteFilter filter);

}
