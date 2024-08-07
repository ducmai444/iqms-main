package vn.com.itechcorp.module.speaker.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import vn.com.itechcorp.base.service.dto.DtoGet;
import vn.com.itechcorp.module.speaker.model.Speaker;

@Getter
@Setter
@NoArgsConstructor
public class SpeakerDtoGet extends DtoGet<Speaker, Long> {

    private String name;

    public SpeakerDtoGet(Speaker speaker){
        super(speaker);
    }

    @Override
    public void parse(Speaker speaker) {
        this.name = speaker.getName();
    }
}
