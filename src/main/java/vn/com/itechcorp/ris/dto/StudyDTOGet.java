package vn.com.itechcorp.ris.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import vn.com.itechcorp.base.service.dto.Dto;
import vn.com.itechcorp.module.modality.dto.ModalityDTOGet;

@Getter
@Setter
@NoArgsConstructor
public class StudyDTOGet extends Dto {

    private Long id;

    private ModalityDTOGet modality;

}

