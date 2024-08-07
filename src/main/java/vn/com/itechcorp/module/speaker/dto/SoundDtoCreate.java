package vn.com.itechcorp.module.speaker.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import vn.com.itechcorp.base.service.dto.BaseDtoCreate;
import vn.com.itechcorp.module.speaker.model.Sound;

@Getter @Setter
@AllArgsConstructor
@NoArgsConstructor
public class SoundDtoCreate extends BaseDtoCreate<Sound, Long> {

    private Long ticketId;

    @Override
    public Sound toEntry() {
        Sound s = new Sound();
        s.setTicketId(ticketId);
        return s;
    }

    @JsonIgnore
    @Override
    public Long getId() {
        return null;
    }
}
