package vn.com.itechcorp.module.ticket.dto;

import lombok.Getter;
import lombok.Setter;
import vn.com.itechcorp.base.service.dto.DtoCreate;
import vn.com.itechcorp.module.ticket.model.TicketProcedure;

import javax.validation.constraints.NotNull;

@Getter
@Setter
public class TicketProcedureDTOCreate extends DtoCreate<TicketProcedure, Long> {

    @NotNull
    private Long orderID;

    @NotNull
    private Long serviceID;

    @NotNull
    private Long procedureID;

    private String procedureCode;

    private String procedureName;

    private String accessionNumber;

    @Override
    public TicketProcedure toEntry() {
        TicketProcedure ticketProcedure = new TicketProcedure();
        ticketProcedure.setOrderID(this.orderID);
        ticketProcedure.setServiceID(this.serviceID);
        ticketProcedure.setProcedureID(this.procedureID);
        ticketProcedure.setProcedureCode(this.procedureCode);
        ticketProcedure.setProcedureName(this.procedureName);
        ticketProcedure.setAccessionNumber(this.accessionNumber);

        return ticketProcedure;
    }

}
