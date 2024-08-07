package vn.com.itechcorp.module.ticket.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import vn.com.itechcorp.module.ticket.model.TicketCounter;
import vn.com.itechcorp.module.ticket.repository.TicketCounterRepository;

import java.util.Date;

@Service
@Transactional(propagation = Propagation.REQUIRES_NEW)
public class TicketCounterServiceImpl implements TicketCounterService {

    @Autowired
    private TicketCounterRepository ticketCounterRepository;

    @Override
    public Integer getCurrentCounterNumber(String id) {
        TicketCounter counter = ticketCounterRepository.findById(id).orElse(null);
        if (counter == null) {
            counter = new TicketCounter();
            counter.setId(id);
            counter.setNumber(1);
        } else {
            counter.setNumber(counter.getNumber() + 1);
        }

        counter.setDateUpdated(new Date());
        ticketCounterRepository.save(counter);
        return counter.getNumber();
    }

}
