package vn.com.itechcorp.module.modality.repository;

import org.springframework.stereotype.Repository;
import vn.com.itechcorp.base.persistence.repository.BaseRepository;
import vn.com.itechcorp.module.modality.model.Modality;

import java.util.List;

@Repository("modalityRepository")
public interface ModalityRepository extends BaseRepository<Modality, Long> {

    List<Modality> findAllBySiteID(Long siteID);

}
