package vn.com.itechcorp.module.ticket.repository;

import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import vn.com.itechcorp.base.persistence.repository.BaseRepository;
import vn.com.itechcorp.module.ticket.model.TicketCounter;

import javax.transaction.Transactional;

@Repository
public interface TicketCounterRepository extends BaseRepository<TicketCounter, String> {

    @Modifying
    @Transactional
    @Query(value = "TRUNCATE TABLE qms_ticket_counter CASCADE", nativeQuery = true)
    int truncateTable();

}
