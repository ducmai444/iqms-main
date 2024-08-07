package vn.com.itechcorp.module.site.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import vn.com.itechcorp.base.service.dto.DtoCreate;
import vn.com.itechcorp.module.modality.dto.ModalityDtoCreate;
import vn.com.itechcorp.module.site.model.Site;

import javax.validation.constraints.NotNull;
import java.util.List;
import java.util.stream.Collectors;

@Getter @Setter
@NoArgsConstructor
@AllArgsConstructor
public class SiteDtoCreate extends DtoCreate<Site, Long> {

    @NotNull
    private String name;

    private String modalityTypes;

    private List<ModalityDtoCreate> modalities;

    @Override
    public Site toEntry() {
        Site site = new Site();
        site.setId(getId());
        site.setName(name);
        site.setModalityTypes(modalityTypes);
        if(modalities != null && !modalities.isEmpty()){
            site.setModalities(modalities.stream().map(ModalityDtoCreate::toEntry).collect(Collectors.toSet()));
        }
        return site;
    }
}
