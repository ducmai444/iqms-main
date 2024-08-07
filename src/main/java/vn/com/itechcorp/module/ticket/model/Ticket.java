package vn.com.itechcorp.module.ticket.model;

import lombok.Getter;
import lombok.Setter;
import vn.com.itechcorp.base.persistence.model.AuditableSerialIDEntry;

import javax.persistence.*;
import java.util.Set;

@Entity
@Table(name = "qms_ticket")
@NamedEntityGraph(
        name = "ticketGraph",
        attributeNodes = {
                @NamedAttributeNode("ticketProcedures"),
        }
)
@Getter
@Setter
public class Ticket extends AuditableSerialIDEntry {

    @Column(name = "modality_id", nullable = false)
    private Long modalityID;

    @Column(name = "patient_pid")
    private String pid;

    @Column(name = "patient_name")
    private String patientName;

    @Column(name = "patient_name_search")
    private String patientNameSearch;

    @Column(name = "gender")
    private String gender;

    @Column(name = "birth_date")
    private String birthDate;

    @Column(name = "address")
    private String address;

    @Column(name = "ticket_number")
    private String ticketNumber;

    @Column(name = "status")
    @Enumerated(EnumType.ORDINAL)
    private TicketStatus status;

    @OneToMany(mappedBy = "ticket", fetch = FetchType.LAZY)
    private Set<TicketProcedure> ticketProcedures;

}
