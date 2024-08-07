package vn.com.itechcorp.module.speaker.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import vn.com.itechcorp.base.service.dto.DtoCreate;
import vn.com.itechcorp.module.speaker.model.Speaker;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

@Getter @Setter
@NoArgsConstructor
@AllArgsConstructor
public class SpeakerDtoCreate extends DtoCreate<Speaker, Long> {

    @NotNull
    @NotEmpty
    private String name;


    @Override
    public Speaker toEntry() {
        Speaker speaker = new Speaker();
        speaker.setId(getId());
        speaker.setName(name);
        return speaker;
    }
}
