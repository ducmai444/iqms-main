package vn.com.itechcorp.module.ticket.dto;

import lombok.Getter;
import lombok.Setter;
import vn.com.itechcorp.base.service.dto.DtoGet;
import vn.com.itechcorp.module.ticket.model.TicketProcedure;

@Getter
@Setter
public class TicketProcedureDTOGet extends DtoGet<TicketProcedure, Long> {

    private Long ticketID;

    private Long serviceID;

    private Long orderID;

    private Long procedureID;

    private String procedureCode;

    private String procedureName;

    private String accessionNumber;

    public TicketProcedureDTOGet(TicketProcedure object) {
        super(object);
    }

    @Override
    public void parse(TicketProcedure object) {
        this.ticketID = object.getTicketID();
        this.orderID = object.getOrderID();
        this.serviceID = object.getServiceID();
        this.accessionNumber = object.getAccessionNumber();
        this.procedureID = object.getProcedureID();
        this.procedureName = object.getProcedureName();
        this.procedureCode = object.getProcedureCode();
    }

}
