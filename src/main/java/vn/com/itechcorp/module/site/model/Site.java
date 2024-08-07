package vn.com.itechcorp.module.site.model;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import vn.com.itechcorp.base.persistence.model.BaseDbEntry;
import vn.com.itechcorp.module.modality.model.Modality;

import javax.persistence.*;
import java.util.Set;

@Entity
@Table(name = "qms_site")
@Getter @Setter
@NoArgsConstructor
public class Site extends BaseDbEntry<Long> {

    @Column(name = "name", nullable = false)
    private String name;

    @OneToMany(mappedBy = "site", fetch = FetchType.EAGER)
    private Set<Modality> modalities;

    @Column(name = "modality_types", nullable = false)
    private String modalityTypes;

    public Site(Long id){
        this.setId(id);
    }

}
