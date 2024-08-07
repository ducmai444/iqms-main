package vn.com.itechcorp.module.ticket.model;

import lombok.Getter;
import lombok.Setter;
import vn.com.itechcorp.base.persistence.model.BaseDbEntry;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import java.util.Date;

@Entity
@Table(name="qms_ticket_counter")
@Getter
@Setter
public class TicketCounter extends BaseDbEntry<String> { // ID is modality_type

    @Column(name="current_number")
    private Integer number;

    @Column(name = "last_updated_date")
    private Date dateUpdated;

}
