package ch.zuegi.ordermgmt.feature.ticket.domain.event;

import ch.zuegi.ordermgmt.feature.ticket.domain.entity.TicketLifeCycleState;
import ch.zuegi.ordermgmt.feature.ticket.domain.vo.TicketId;
import ch.zuegi.ordermgmt.shared.DomainEvent;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;



@Builder
@Getter
public class TicketLifecycleUpdated implements DomainEvent {
    TicketId ticketNumber;
    LocalDateTime localDateTime;
    TicketLifeCycleState currentLifeCycleState;
    TicketLifeCycleState previousLifeCycleState;

    @Override
    public int eventVersion() {
        return 0;
    }

    @Override
    public LocalDateTime occurredOn() {
        return localDateTime;
    }
}

