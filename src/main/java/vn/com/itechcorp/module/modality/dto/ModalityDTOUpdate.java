package vn.com.itechcorp.module.modality.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import vn.com.itechcorp.base.service.dto.DtoUpdate;
import vn.com.itechcorp.module.modality.model.Modality;

import java.util.Date;

@Getter
@Setter
@NoArgsConstructor
public class ModalityDTOUpdate extends DtoUpdate<Modality, Long> {

    private Boolean enabled;

    private Date lastUpdatedTime;

    private String lastUpdatedBy;

    @Override
    public boolean apply(Modality object) {
        boolean modified = false;

        if (enabled != null) {
            object.setEnabled(enabled);
            modified = true;
        }
        if (lastUpdatedBy != null) {
            object.setLastUpdatedName(lastUpdatedBy);
            modified = true;
        }
        if (lastUpdatedTime != null) {
            object.setLastUpdatedTime(lastUpdatedTime);
            modified = true;
        }

        return modified;
    }
}
