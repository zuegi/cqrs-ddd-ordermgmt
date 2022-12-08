package ch.zuegi.ordermgmt.feature.ticket.domain;

import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
public class TicketRepository {

    // TODO findByTicketId ausprogrammieren - falls nötig
    public Optional<Ticket> findByTicketId() {
        return Optional.empty();
    }
}
