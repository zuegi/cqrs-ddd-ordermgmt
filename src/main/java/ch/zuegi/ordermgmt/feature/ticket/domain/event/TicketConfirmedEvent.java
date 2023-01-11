package ch.zuegi.ordermgmt.feature.ticket.domain.event;

import ch.zuegi.ordermgmt.feature.ticket.domain.entity.TicketLifeCycleState;
import ch.zuegi.ordermgmt.feature.ticket.domain.vo.TicketId;
import ch.zuegi.ordermgmt.shared.DomainEvent;
import lombok.Builder;
import lombok.Getter;
import lombok.ToString;

import java.time.LocalDateTime;

@Getter
@Builder
@ToString
public class TicketConfirmedEvent extends DomainEvent<TicketConfirmedEvent, TicketId> {
    TicketId ticketId;
    TicketLifeCycleState ticketLifeCycleState;

    @Override
    public TicketConfirmedEvent getEvent() {
        return this;
    }

    @Override
    public int eventVersion() {
        return 0;
    }

    @Override
    public LocalDateTime occurredOn() {
        return LocalDateTime.now();
    }

    @Override
    public TicketId id() {
        return ticketId;
    }
}
