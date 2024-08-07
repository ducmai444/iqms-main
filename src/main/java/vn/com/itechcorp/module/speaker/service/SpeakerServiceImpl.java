package vn.com.itechcorp.module.speaker.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import vn.com.itechcorp.base.service.impl.BaseDtoJpaServiceImpl;
import vn.com.itechcorp.module.speaker.dto.SpeakerDtoGet;
import vn.com.itechcorp.module.speaker.model.Speaker;
import vn.com.itechcorp.module.speaker.repository.SpeakerRepository;

@Service("speakerService")
public class SpeakerServiceImpl extends BaseDtoJpaServiceImpl<SpeakerDtoGet, Speaker, Long> implements SpeakerService {

    @Autowired
    private SpeakerRepository soundRepository;

    @Override
    public SpeakerRepository getRepository() {
        return soundRepository;
    }

    @Override
    public SpeakerDtoGet convert(Speaker speaker) {
        return new SpeakerDtoGet(speaker);
    }

}
