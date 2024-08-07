package vn.com.itechcorp.module.modality.service;

import vn.com.itechcorp.base.service.BaseDtoService;
import vn.com.itechcorp.module.modality.dto.ModalityDTOGet;
import vn.com.itechcorp.module.modality.dto.ModalityTypeFilter;
import vn.com.itechcorp.module.modality.model.Modality;

import java.util.Collection;
import java.util.List;

public interface ModalityService extends BaseDtoService<ModalityDTOGet, Modality, Long> {

    void fetchLazyInformation(List<ModalityDTOGet> modalities);

    List<String> getModalityTypes();

    Collection<String> getModalityTypes(ModalityTypeFilter filter);

    List<ModalityDTOGet> getById(List<Long> ids);

    List<ModalityDTOGet> getBySiteID(Long siteID);

}
