package vn.com.itechcorp.module.site.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import vn.com.itechcorp.base.service.dto.DtoGet;
import vn.com.itechcorp.module.modality.dto.ModalityDTOGet;
import vn.com.itechcorp.module.site.model.Site;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@Getter @Setter @NoArgsConstructor
public class SiteDTOGet extends DtoGet<Site, Long> {

    private String name;

    private List<ModalityDTOGet> modalities;

    private List<String> modalityTypes;

    public SiteDTOGet(Site site) {
        super(site);
    }

    @Override
    public void parse(Site object) {
        this.name = object.getName();
        if (object.getModalities() != null) {
            this.modalities = object.getModalities().stream().map(ModalityDTOGet::new).collect(Collectors.toList());
        }
        if (object.getModalityTypes() != null) {
            this.modalityTypes = Arrays.asList(object.getModalityTypes().split(","));
        }
    }

}
