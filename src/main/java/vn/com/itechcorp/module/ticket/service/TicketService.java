package vn.com.itechcorp.module.ticket.service;

import vn.com.itechcorp.base.service.AuditableDtoService;
import vn.com.itechcorp.module.ticket.dto.TicketSummaryDto;
import vn.com.itechcorp.module.ticket.dto.TicketDTOGet;
import vn.com.itechcorp.module.ticket.dto.TicketSummaryFilter;
import vn.com.itechcorp.module.ticket.model.Ticket;

import java.util.List;

public interface TicketService extends AuditableDtoService<TicketDTOGet, Ticket, Long> {

    List<TicketSummaryDto> getTicketSummary(TicketSummaryFilter filter);
}
