package vn.com.itechcorp.module.modality.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import vn.com.itechcorp.base.service.dto.DtoCreate;
import vn.com.itechcorp.module.modality.model.Modality;

import javax.validation.constraints.NotNull;
import java.util.Date;

@Getter @Setter
@NoArgsConstructor
@AllArgsConstructor
public class ModalityDtoCreate extends DtoCreate<Modality, Long> {

    private Long siteID;

    private Long speakerId;

    @NotNull
    private String name;

    @NotNull
    private String code;

    private String modalityType;

    private String otherModalityTypes;

    private String roomName;

    private Boolean enabled;

    private String creatorName;

    private Date createdTime;

    @Override
    public Modality toEntry() {
        Modality modality = new Modality();
        modality.setId(getId());
        modality.setSiteID(siteID);
        modality.setSpeakerId(speakerId);
        modality.setName(name);
        modality.setCode(code);
        modality.setModalityType(modalityType);
        modality.setOtherModalityTypes(otherModalityTypes);
        modality.setRoomName(roomName);
        modality.setEnabled(enabled);
        modality.setCreatorName(creatorName);
        modality.setCreatedTime(createdTime);
        return modality;
    }
}
