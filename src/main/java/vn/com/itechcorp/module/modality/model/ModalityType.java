package vn.com.itechcorp.module.modality.model;

import lombok.Getter;
import lombok.Setter;
import vn.com.itechcorp.base.persistence.model.BaseDbEntry;

import javax.persistence.*;

@Entity
@Table(name = "qms_modality_type")
@Getter
@Setter
public class ModalityType extends BaseDbEntry<String> {
}
