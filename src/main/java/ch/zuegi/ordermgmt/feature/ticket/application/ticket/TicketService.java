package ch.zuegi.ordermgmt.feature.ticket.application.ticket;

import ch.zuegi.ordermgmt.feature.ticket.domain.Ticket;
import ch.zuegi.ordermgmt.feature.ticket.domain.entity.TicketEntity;
import ch.zuegi.ordermgmt.feature.ticket.domain.vo.TicketNumber;
import ch.zuegi.ordermgmt.feature.ticket.infrastructure.persistence.TicketRepository;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@AllArgsConstructor
@Service
@Transactional(propagation = Propagation.REQUIRES_NEW)
public class TicketService {

    TicketRepository ticketRepository;

    public void createTicket(Ticket ticket) {
        ticketRepository.save(ticket.getTicketEntity());
    }

    public TicketEntity findByTicketNumber(TicketNumber ticketNumber) {
        return ticketRepository.findByTicketNumber(ticketNumber);
    }
}
