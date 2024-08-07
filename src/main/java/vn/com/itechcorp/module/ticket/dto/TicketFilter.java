package vn.com.itechcorp.module.ticket.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import vn.com.itechcorp.base.service.filter.BaseFilter;
import vn.com.itechcorp.module.ticket.model.Ticket;
import vn.com.itechcorp.module.ticket.model.TicketStatus;
import vn.com.itechcorp.module.ticket.model.Ticket_;

import javax.persistence.criteria.*;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class TicketFilter implements BaseFilter<Ticket> {

    private String pid;

    private String patientName;

    private Long modalityID;

    // TODO: This is a hack to be API backward compatible, need to remove this when new feature speaker is merged
    @JsonFormat(with = JsonFormat.Feature.ACCEPT_SINGLE_VALUE_AS_ARRAY)
    private List<TicketStatus> status;

    @Override
    public Predicate toPredicate(Root<Ticket> root, CriteriaQuery<?> query, CriteriaBuilder builder) {
        final List<Predicate> predicates = new ArrayList<>();

        if (pid != null) predicates.add(builder.equal(root.get(Ticket_.PID), pid));

        if (patientName != null)
            predicates.add(builder.like(root.get(Ticket_.PATIENT_NAME_SEARCH), "%" + patientName.toUpperCase() + "%"));

        if (modalityID != null) predicates.add(builder.equal(root.get(Ticket_.MODALITY_ID), modalityID));

        if (status != null) predicates.add(root.get(Ticket_.STATUS).in(status));

        query.distinct(true);

        return builder.and(predicates.toArray(new Predicate[0]));
    }
}
