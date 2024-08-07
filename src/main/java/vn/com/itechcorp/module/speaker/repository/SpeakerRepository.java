package vn.com.itechcorp.module.speaker.repository;

import org.springframework.stereotype.Repository;
import vn.com.itechcorp.base.persistence.repository.BaseRepository;
import vn.com.itechcorp.module.speaker.model.Speaker;

@Repository("speakerRepository")
public interface SpeakerRepository extends BaseRepository<Speaker, Long> {
}
