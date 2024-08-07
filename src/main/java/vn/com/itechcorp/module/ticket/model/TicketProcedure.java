package vn.com.itechcorp.module.ticket.model;

import lombok.Getter;
import lombok.Setter;
import vn.com.itechcorp.base.persistence.model.BaseSerialIDEntry;

import javax.persistence.*;

@Entity
@Table(name = "qms_ticket_procedure")
@Getter
@Setter
public class TicketProcedure extends BaseSerialIDEntry {

    @Column(name = "ticket_id", nullable = false, insertable = false, updatable = false)
    private Long ticketID;

    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    @JoinColumn(name = "ticket_id")
    private Ticket ticket;

    @Column(name = "ris_service_id", nullable = false)
    private Long serviceID;

    @Column(name = "ris_order_id", nullable = false)
    private Long orderID;

    @Column(name = "procedure_id")
    private Long procedureID;

    @Column(name = "procedure_code")
    private String procedureCode;

    @Column(name = "procedure_name")
    private String procedureName;

    @Column(name = "ris_accession_number")
    private String accessionNumber;

}
