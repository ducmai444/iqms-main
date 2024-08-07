package vn.com.itechcorp.module.ticket.dto;

import lombok.Getter;
import lombok.Setter;
import vn.com.itechcorp.base.service.dto.BaseDtoCreate;
import vn.com.itechcorp.base.util.TextUtil;
import vn.com.itechcorp.module.ticket.model.Ticket;
import vn.com.itechcorp.module.ticket.model.TicketStatus;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@Getter
@Setter
public class TicketDTOCreate extends BaseDtoCreate<Ticket, Long> {

    @NotNull
    private Long modalityID;

    @NotNull
    @NotEmpty
    private String pid;

    @NotNull
    @NotEmpty
    private String patientName;

    private String gender;

    private String birthDate;

    private String address;

    @NotNull
    @NotEmpty
    private List<TicketProcedureDTOCreate> services;

    @Override
    public Long getId() {
        return null;
    }

    @Override
    public Ticket toEntry() {
        Ticket ticket = new Ticket();
        ticket.setModalityID(this.modalityID);
        ticket.setPid(this.pid);
        ticket.setPatientName(this.patientName);
        ticket.setPatientNameSearch(TextUtil.getInstance().toASCIIUpperCase(this.patientName, true));
        ticket.setBirthDate(this.birthDate);
        ticket.setAddress(this.address);
        ticket.setGender(this.getGender());
        if (services != null && !services.isEmpty()) {
            ticket.setTicketProcedures(services.stream().map(TicketProcedureDTOCreate::toEntry).collect(Collectors.toSet()));
        }
        ticket.setStatus(TicketStatus.STARTED);

        Date date = new Date();
        ticket.setCreator(1L);
        ticket.setDateCreated(date);
        ticket.setUpdater(1L);
        ticket.setDateUpdated(date);

        return ticket;
    }
}
