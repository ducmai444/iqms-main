package vn.com.itechcorp.module.ticket.repository;

import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import vn.com.itechcorp.base.persistence.repository.BaseRepository;
import vn.com.itechcorp.module.ticket.model.TicketProcedure;

import javax.transaction.Transactional;
import java.util.List;

@Repository("ticketProcedureRepository")
public interface TicketProcedureRepository extends BaseRepository<TicketProcedure, Long> {

    List<TicketProcedure> getTicketProceduresByServiceIDIn(Iterable<Long> serviceIDs);

    @Modifying
    @Transactional
    @Query(value = "TRUNCATE TABLE qms_ticket_procedure CASCADE ", nativeQuery = true)
    int truncateTable();

}
