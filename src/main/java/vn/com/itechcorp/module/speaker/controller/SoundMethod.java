package vn.com.itechcorp.module.speaker.controller;

import org.springframework.stereotype.Service;
import vn.com.itechcorp.base.api.method.AsyncBaseDtoAPIMethod;
import vn.com.itechcorp.module.speaker.dto.SoundDtoGet;
import vn.com.itechcorp.module.speaker.model.Sound;
import vn.com.itechcorp.module.speaker.service.SoundService;

@Service
public class SoundMethod extends AsyncBaseDtoAPIMethod<SoundDtoGet, Sound, Long> {

    public SoundMethod(SoundService service) {
        super(service);
    }

}
