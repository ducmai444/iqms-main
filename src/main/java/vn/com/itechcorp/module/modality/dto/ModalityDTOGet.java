package vn.com.itechcorp.module.modality.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import vn.com.itechcorp.base.service.dto.DtoGet;
import vn.com.itechcorp.module.modality.model.Modality;

@Getter @Setter @NoArgsConstructor
public class ModalityDTOGet extends DtoGet<Modality, Long> {

    private Long siteID;

    private Long speakerID;

    private String name;

    private String code;

    private String modalityType;

    private String otherModalityTypes;

    private Boolean enabled;

    private String roomName;

    private int totalPatients;

    private int totalCompletedPatients;

    private String currentNumber;


    public ModalityDTOGet(Modality object) {
        super(object);
    }

    @Override
    public void parse(Modality object) {
        this.siteID = object.getSiteID();
        this.speakerID = object.getSpeakerId();
        this.name = object.getName();
        this.code = object.getCode();
        this.modalityType = object.getModalityType();
        this.otherModalityTypes = object.getOtherModalityTypes();
        this.roomName = object.getRoomName();
        this.enabled = object.getEnabled();
    }

}
