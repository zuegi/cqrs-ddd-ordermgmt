package ch.zuegi.ordermgmt.feature.ticket.domain.event;

import ch.zuegi.ordermgmt.feature.ticket.domain.entity.TicketLifeCycleState;
import ch.zuegi.ordermgmt.feature.ticket.domain.vo.TicketId;
import ch.zuegi.ordermgmt.shared.DomainEvent;
import lombok.Builder;
import lombok.Getter;
import lombok.ToString;

import java.time.LocalDateTime;



@Builder
@Getter
@ToString
public class TicketLifecycleUpdatedEvent extends DomainEvent<TicketLifecycleUpdatedEvent, TicketId> {

    TicketId ticketId;
    LocalDateTime localDateTime;
    TicketLifeCycleState currentLifeCycleState;
    TicketLifeCycleState previousLifeCycleState;

    @Override
    public TicketLifecycleUpdatedEvent getEvent() {
        return this;
    }

    @Override
    public int eventVersion() {
        return 0;
    }

    @Override
    public LocalDateTime occurredOn() {
        return localDateTime;
    }

    @Override
    public TicketId id() {
        return ticketId;
    }
}

