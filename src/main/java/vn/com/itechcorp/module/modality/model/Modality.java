package vn.com.itechcorp.module.modality.model;

import lombok.Getter;
import lombok.Setter;
import vn.com.itechcorp.base.persistence.model.BaseDbEntry;
import vn.com.itechcorp.module.site.model.Site;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name = "qms_modality")
@Getter
@Setter
public class Modality extends BaseDbEntry<Long> {

    @Column(name = "site_id", nullable = false, insertable = false, updatable = false)
    private Long siteID;

    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    @JoinColumn(name = "site_id")
    private Site site;

    @Column(name = "speaker_id")
    private Long speakerId;

    @Column(name = "name", nullable = false)
    private String name;

    @Column(name = "code")
    private String code;

    @Column(name = "modality_type")
    private String modalityType;

    @Column(name = "other_modality_types")
    private String otherModalityTypes;

    @Column(name = "room_name")
    private String roomName;

    @Column(name = "room_name_audio")
    private byte[] roomNameAudio;

    @Column(name = "enabled")
    private Boolean enabled;

    @Column(name = "created_time")
    private Date createdTime;

    @Column(name = "creator_name")
    private String creatorName;

    @Column(name = "last_updated_time")
    private Date lastUpdatedTime;

    @Column(name = "last_updated_name")
    private String lastUpdatedName;

}
