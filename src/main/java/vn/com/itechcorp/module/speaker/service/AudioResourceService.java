package vn.com.itechcorp.module.speaker.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import vn.com.itechcorp.module.speaker.repository.SoundRepository;

import java.util.List;

@Service
@Transactional(propagation = Propagation.REQUIRES_NEW)
public class AudioResourceService {

    @Autowired
    private SoundRepository soundRepository;

    public void deleteByIDs(List<Long> ids) {
        soundRepository.deleteByIdIn(ids);
    }

}
