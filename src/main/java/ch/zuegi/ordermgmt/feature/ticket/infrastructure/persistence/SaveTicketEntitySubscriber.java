package ch.zuegi.ordermgmt.feature.ticket.infrastructure.persistence;

import ch.zuegi.ordermgmt.feature.ticket.domain.entity.TicketEntity;
import ch.zuegi.ordermgmt.feature.ticket.domain.event.TicketSaveEvent;
import ch.zuegi.ordermgmt.shared.DomainEventPublisher;
import ch.zuegi.ordermgmt.shared.DomainEventSubscriber;
import org.springframework.stereotype.Component;

import java.util.HashSet;

@Component
public class SaveTicketEntitySubscriber implements DomainEventSubscriber<TicketSaveEvent> {

    DomainEventPublisher domainEventPublisher;
    TicketRepository ticketRepository;

    public SaveTicketEntitySubscriber(TicketRepository ticketRepository) {
        this.ticketRepository = ticketRepository;
        DomainEventPublisher.instance().subscribe(this);
    }

    @Override
    public void handleEvent(TicketSaveEvent aDomainEvent) {
        System.out.println("Hier wird der Event in einer Entity gespeichert");
        TicketEntity ticket = new TicketEntity();
        ticket.setTicketNumber(aDomainEvent.getTicketNumber());
        ticket.setLifeCycleState(aDomainEvent.getLifeCycleState());
        ticket.setLocalDateTime(aDomainEvent.getLocalDateTime());
        ticket.setTicketPositionEntitySet(new HashSet<>());
        this.ticketRepository.save(ticket);
    }

    @Override
    public Class<TicketSaveEvent> subscribedToEventType() {
        return TicketSaveEvent.class;
    }
}
