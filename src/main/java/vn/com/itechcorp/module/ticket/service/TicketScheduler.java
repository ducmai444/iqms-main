package vn.com.itechcorp.module.ticket.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import vn.com.itechcorp.base.service.filter.SpecBuilder;
import vn.com.itechcorp.module.speaker.repository.SoundRepository;
import vn.com.itechcorp.module.ticket.dto.TicketFilter;
import vn.com.itechcorp.module.ticket.model.Ticket;
import vn.com.itechcorp.module.ticket.model.TicketProcedure;
import vn.com.itechcorp.module.ticket.model.TicketStatus;
import vn.com.itechcorp.module.ticket.repository.TicketCounterRepository;
import vn.com.itechcorp.module.ticket.repository.TicketProcedureRepository;
import vn.com.itechcorp.module.ticket.repository.TicketRepository;
import vn.com.itechcorp.ris.dto.OrderDTOGet;
import vn.com.itechcorp.ris.service.RisService;

import java.util.Arrays;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;


@Service
public class TicketScheduler {

    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private TicketRepository ticketRepository;

    @Autowired
    private TicketProcedureRepository ticketProcedureRepository;

    @Autowired
    private TicketCounterRepository ticketCounterRepository;

    @Autowired
    private SoundRepository soundRepository;

    @Autowired
    private RisService risService;

    @Scheduled(cron = "${qms.cron.scan-orders:-}")
    public void scan() {
        logger.debug("[TicketScheduler] Scanning");
        List<Ticket> tickets = ticketRepository.findAll(SpecBuilder.createSpecification(new TicketFilter(null, null, null,
                Arrays.asList(TicketStatus.STARTED, TicketStatus.PASSING))));
        if (tickets.isEmpty()) return;

        Set<Long> orderIDs = tickets.stream().flatMap(t -> t.getTicketProcedures().stream()).map(TicketProcedure::getOrderID).collect(Collectors.toSet());

        List<OrderDTOGet> orders = risService.getOrders(orderIDs);

        if (orders.isEmpty()) return;
        Set<Long> imagingOrderIDs = orders.stream().filter(o -> o.getStudyID() != null).map(OrderDTOGet::getId).collect(Collectors.toSet());

        for (Ticket ticket : tickets) {
            boolean image = true;
            for (TicketProcedure service : ticket.getTicketProcedures()) {
                image &= imagingOrderIDs.contains(service.getOrderID());
            }

            if (image) {
                ticket.setStatus(TicketStatus.COMPLETED);
                ticketRepository.save(ticket);
            }
        }
    }

    @Scheduled(cron = "${qms.cron.remove-tickets:-}")
    public void removeOldTickets() {
        try {
            logger.info("[TicketScheduler] Begin truncating ...");

            logger.info("[TicketScheduler] TRUNCATE TABLE qms_ticket_procedure CASCADE ");
            ticketProcedureRepository.truncateTable();

            logger.info("[TicketScheduler] TRUNCATE TABLE qms_ticket CASCADE ");
            ticketRepository.truncateTable();

            logger.info("[TicketScheduler] TRUNCATE TABLE qms_ticket_counter CASCADE ");
            ticketCounterRepository.truncateTable();

            logger.info("[TicketScheduler] TRUNCATE TABLE qms_sound CASCADE ");
            soundRepository.truncateTable();

            logger.info("[TicketScheduler] Finish truncating");
        } catch (Exception ex) {
            logger.error("[TicketScheduler] truncating error {}", ex.getLocalizedMessage());
        }
    }

}
