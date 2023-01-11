package ch.zuegi.ordermgmt.feature.ticket.domain.handler;

import ch.zuegi.ordermgmt.feature.ticket.domain.entity.TicketRepository;
import ch.zuegi.ordermgmt.feature.ticket.domain.vo.TicketId;
import ch.zuegi.ordermgmt.shared.DomainEvent;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

@Slf4j
@AllArgsConstructor
@Component
public class TicketDomainHandler {

    private TicketRepository repository;
    private ApplicationEventPublisher applicationEventPublisher;

    @EventListener
    public void handleTicketEvents(DomainEvent<?, TicketId> domainEvent) {
        log.info(domainEvent.getClass().getSimpleName() + ": " + domainEvent);
        repository.save(domainEvent);

        // TODO hier über die Domain Grenze hinweg einen einen Event ausspucken für den TicketViewHandler
//        applicationEventPublisher.publishEvent(einDomainViewEvent);
    }

}
