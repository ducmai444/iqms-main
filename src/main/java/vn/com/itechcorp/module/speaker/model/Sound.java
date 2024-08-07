package vn.com.itechcorp.module.speaker.model;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import vn.com.itechcorp.base.persistence.model.BaseSerialIDEntry;

import javax.persistence.*;

@Entity
@Table(name = "qms_sound")
@Getter
@Setter
@NoArgsConstructor
public class Sound extends BaseSerialIDEntry {

    @Column(name="ticket_id")
    private Long ticketId;

    @Column(name="modality_id")
    private Long modalityId;

    @Column(name = "modality_room")
    private String modalityRoom;

    @Column(name="speaker_id")
    private Long speakerId;

    @Column(name = "ticket_number")
    private String ticketNumber;

    @Column(name = "patient_name")
    private String patientName;

    @Column(name="audio")
    private byte[] audio;
}
