package ch.zuegi.ordermgmt.feature.ticket.domain.handler;

import ch.zuegi.ordermgmt.feature.ticket.domain.TicketRepository;
import ch.zuegi.ordermgmt.feature.ticket.domain.event.TicketCreatedEvent;
import ch.zuegi.ordermgmt.feature.ticket.domain.event.TicketPositionAddedEvent;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

@Slf4j
@AllArgsConstructor
@Component
public class TicketDomainHandler {

    private TicketRepository repository;

    @EventListener
    public void handleTicketCreatedEvent(TicketCreatedEvent event) {
        log.info("handleTicketCreatedEvent: {}", event);
        // muss der event hier nochmals validiert werden?
        repository.save(event);
    }


    // hier die anderen Events abfackeln
    // Tests schreiben

    @EventListener
    public void handleTicketPositionAddedEvent(TicketPositionAddedEvent ticketPositionAddedEvent) {
        log.info("TicketPositionAddedEvent: {}", ticketPositionAddedEvent);
        repository.save(ticketPositionAddedEvent);
    }

}
