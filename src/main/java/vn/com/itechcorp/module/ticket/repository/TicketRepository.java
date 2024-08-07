package vn.com.itechcorp.module.ticket.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import vn.com.itechcorp.base.persistence.repository.AuditableRepository;
import vn.com.itechcorp.module.ticket.model.Ticket;
import vn.com.itechcorp.module.ticket.model.TicketCount;
import vn.com.itechcorp.module.ticket.model.TicketStatus;

import javax.transaction.Transactional;
import java.util.List;

@Repository("ticketRepository")
public interface TicketRepository extends AuditableRepository<Ticket, Long> {

    List<Ticket> findAllByModalityIDIn(Iterable<Long> modalityID);

    List<Ticket> findAllByModalityIDInAndStatusNotIn(Iterable<Long> modalityID, Iterable<TicketStatus> statuses);

    @Query(value = "SELECT new vn.com.itechcorp.module.ticket.model.TicketCount( t.modalityID, COUNT(t.id)) FROM Ticket t WHERE t.modalityID in (:modalityIDs) GROUP BY t.modalityID")
    List<TicketCount> countTotalTicketsByModality(Iterable<Long> modalityIDs);

    @Override
    @EntityGraph(value = "ticketGraph", type = EntityGraph.EntityGraphType.FETCH)
    List<Ticket> findAllById(Iterable<Long> iterable);

    @Override
    @EntityGraph(value = "ticketGraph", type = EntityGraph.EntityGraphType.FETCH)
    List<Ticket> findAll(Specification<Ticket> specification);

    @Override
    @EntityGraph(value = "ticketGraph", type = EntityGraph.EntityGraphType.FETCH)
    Page<Ticket> findAll(Specification<Ticket> specification, Pageable pageable);

    @Modifying
    @Transactional
    @Query(value = "TRUNCATE TABLE qms_ticket CASCADE", nativeQuery = true)
    int truncateTable();
}
