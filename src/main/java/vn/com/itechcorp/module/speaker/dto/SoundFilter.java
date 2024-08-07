package vn.com.itechcorp.module.speaker.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import vn.com.itechcorp.base.service.filter.BaseFilter;
import vn.com.itechcorp.module.speaker.model.Sound;
import vn.com.itechcorp.module.speaker.model.Sound_;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

@Getter @Setter
@NoArgsConstructor
@AllArgsConstructor
public class SoundFilter implements BaseFilter<Sound> {

    private Long speakerId;
    @Override
    public Predicate toPredicate(Root<Sound> root, CriteriaQuery<?> query, CriteriaBuilder cb) {
        return cb.equal(root.get(Sound_.SPEAKER_ID), speakerId);
    }
}
