package vn.com.itechcorp.module.speaker.repository;

import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import vn.com.itechcorp.base.persistence.repository.BaseRepository;
import vn.com.itechcorp.module.speaker.model.Sound;

import javax.transaction.Transactional;
import java.util.List;

@Repository("soundRepository")
public interface SoundRepository extends BaseRepository<Sound, Long> {

    void deleteByIdIn(List<Long> ids);

    @Modifying
    @Transactional
    @Query(value = "TRUNCATE TABLE qms_sound CASCADE ", nativeQuery = true)
    int truncateTable();

}
