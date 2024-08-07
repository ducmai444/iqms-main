package vn.com.itechcorp.module.speaker.model;

import lombok.Getter;
import lombok.Setter;
import vn.com.itechcorp.base.persistence.model.BaseDbEntry;

import javax.persistence.*;

@Entity
@Table(name = "qms_speaker")
@Getter
@Setter
public class Speaker extends BaseDbEntry<Long> {

    @Column(name = "name", nullable = false)
    private String name;

}
