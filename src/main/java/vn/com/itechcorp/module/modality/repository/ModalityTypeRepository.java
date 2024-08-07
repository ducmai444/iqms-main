package vn.com.itechcorp.module.modality.repository;

import org.springframework.stereotype.Repository;
import vn.com.itechcorp.base.persistence.repository.BaseRepository;
import vn.com.itechcorp.module.modality.model.ModalityType;

@Repository("modalityTypeRepository")
public interface ModalityTypeRepository extends BaseRepository<ModalityType, String> {
}
