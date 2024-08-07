package vn.com.itechcorp.module.ticket.dto;

import lombok.Getter;
import lombok.Setter;
import vn.com.itechcorp.base.service.dto.DtoUpdate;
import vn.com.itechcorp.module.ticket.model.Ticket;
import vn.com.itechcorp.module.ticket.model.TicketStatus;

@Getter
@Setter
public class TicketDTOUpdate extends DtoUpdate<Ticket, Long> {

    private TicketStatus status;

    @Override
    public boolean apply(Ticket ticket) {
        ticket.setStatus(this.status);
        return true;
    }

}
