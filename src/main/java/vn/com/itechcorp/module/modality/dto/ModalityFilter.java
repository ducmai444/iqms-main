package vn.com.itechcorp.module.modality.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import vn.com.itechcorp.base.service.dto.Dto;
import vn.com.itechcorp.base.service.filter.BaseFilter;
import vn.com.itechcorp.module.modality.model.Modality;
import vn.com.itechcorp.module.modality.model.Modality_;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

@Getter
@Setter
@NoArgsConstructor
public class ModalityFilter extends Dto implements BaseFilter<Modality> {

    private Set<Long> ids;

    private List<String> modalityTypes;

    private Long siteID;

    @Override
    public Predicate toPredicate(Root<Modality> root, CriteriaQuery<?> query, CriteriaBuilder builder) {
        final List<Predicate> predicates = new ArrayList<>();

        if (ids != null && !ids.isEmpty()) predicates.add(root.get(Modality_.ID).in(ids));
        if (modalityTypes != null && !modalityTypes.isEmpty())
            predicates.add(root.get(Modality_.MODALITY_TYPE).in(modalityTypes));
        if (siteID != null)
            predicates.add(builder.equal(root.get(Modality_.SITE_ID), siteID));

        return builder.and(predicates.toArray(new Predicate[0]));
    }
}
