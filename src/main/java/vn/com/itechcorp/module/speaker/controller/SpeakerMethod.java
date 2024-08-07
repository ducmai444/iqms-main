package vn.com.itechcorp.module.speaker.controller;

import org.springframework.stereotype.Service;
import vn.com.itechcorp.base.api.method.AsyncBaseDtoAPIMethod;
import vn.com.itechcorp.module.speaker.dto.SpeakerDtoGet;
import vn.com.itechcorp.module.speaker.model.Speaker;
import vn.com.itechcorp.module.speaker.service.SpeakerService;

@Service
public class SpeakerMethod extends AsyncBaseDtoAPIMethod<SpeakerDtoGet, Speaker, Long> {

    public SpeakerMethod(SpeakerService service) {
        super(service);
    }

}
