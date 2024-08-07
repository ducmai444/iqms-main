package vn.com.itechcorp.module.speaker.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import vn.com.itechcorp.base.service.dto.DtoGet;
import vn.com.itechcorp.module.speaker.model.Sound;

@Getter
@Setter
@NoArgsConstructor
public class SoundDtoGet extends DtoGet<Sound, Long> {

    private Long ticketId;

    private Long modalityId;

    private String modalityRoom;

    private Long speakerId;

    private String ticketNumber;

    private String patientName;

    private byte[] audio;

    public SoundDtoGet(Sound sound){
        super(sound);
    }

    @Override
    public void parse(Sound sound) {
        ticketId = sound.getTicketId();
        modalityId = sound.getModalityId();
        modalityRoom = sound.getModalityRoom();
        speakerId = sound.getSpeakerId();
        ticketNumber = sound.getTicketNumber();
        patientName = sound.getPatientName();
    }
}
