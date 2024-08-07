package vn.com.itechcorp.module.procedure.model;

import lombok.Getter;
import lombok.Setter;
import vn.com.itechcorp.base.persistence.model.BaseSerialIDEntry;
import vn.com.itechcorp.module.modality.model.Modality;

import javax.persistence.*;
import java.util.Set;

@Entity
@Table(name = "qms_procedure")
@NamedEntityGraph(
        name = "procedureGraph",
        attributeNodes = {
                @NamedAttributeNode("modalities"),
        }
)
@Getter
@Setter
public class Procedure extends BaseSerialIDEntry {

    @Column(name = "name", nullable = false)
    private String name;

    @Column(name = "code", nullable = false)
    private String code;

    @Column(name = "modality_type")
    private String modalityType;

    @ManyToMany(cascade = CascadeType.MERGE, fetch = FetchType.LAZY)
    @JoinTable(name = "qms_modality_procedure",
            joinColumns = @JoinColumn(name = "procedure_id"),
            inverseJoinColumns = @JoinColumn(name = "modality_id")
    )
    private Set<Modality> modalities;

}
