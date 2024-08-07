package vn.com.itechcorp.module.ticket.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Getter;
import lombok.Setter;
import vn.com.itechcorp.base.service.dto.DtoGet;
import vn.com.itechcorp.module.modality.dto.ModalityDTOGet;
import vn.com.itechcorp.module.ticket.model.Ticket;
import vn.com.itechcorp.module.ticket.model.TicketStatus;

import java.util.Date;
import java.util.List;

@Getter
@Setter
public class TicketDTOGet extends DtoGet<Ticket, Long> {

    private Long modalityID;

    private ModalityDTOGet modality;

    private String pid;

    private String patientName;

    private String birthDate;

    private String ticketNumber;

    private TicketStatus status;

    @JsonFormat(pattern="yyyyMMddHHmmss", timezone="Asia/Bangkok")
    private Date createdDate;

    @JsonFormat(pattern="yyyyMMddHHmmss", timezone="Asia/Bangkok")
    private Date updatedTime;

    private List<TicketProcedureDTOGet> ticketProcedures;

    public TicketDTOGet(Ticket object) {
        super(object);
    }

    @Override
    public void parse(Ticket object) {
        this.modalityID = object.getModalityID();
        this.pid = object.getPid();
        this.patientName = object.getPatientName();
        this.birthDate = object.getBirthDate();
        this.ticketNumber = object.getTicketNumber();
        this.status = object.getStatus();
        this.createdDate = object.getDateCreated();
        this.updatedTime = object.getDateUpdated();
    }

}
