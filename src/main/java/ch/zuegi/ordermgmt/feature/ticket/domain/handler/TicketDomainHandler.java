package ch.zuegi.ordermgmt.feature.ticket.domain.handler;

import ch.zuegi.ordermgmt.feature.ticket.domain.TicketRepository;
import ch.zuegi.ordermgmt.feature.ticket.domain.event.TicketCreatedEvent;
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
        log.info("Received event: {}", event);
        repository.save(event);
    }


}
