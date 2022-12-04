package ch.zuegi.ordermgmt.feature.ticket.domain.event;

import ch.zuegi.ordermgmt.feature.ticket.domain.entity.TicketLifeCycleState;
import ch.zuegi.ordermgmt.feature.ticket.domain.vo.TicketId;
import ch.zuegi.ordermgmt.feature.ticket.domain.vo.TicketPositionId;
import ch.zuegi.ordermgmt.shared.DomainEvent;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;
import java.util.Set;

@Builder
@Getter
public class TicketCreated implements DomainEvent<TicketCreated> {
    TicketId ticketId;
    LocalDateTime localDateTime;
    TicketLifeCycleState lifeCycleState;
    Set<TicketPositionId> ticketPositionNumberSet;

    @Override
    public TicketCreated getEvent() {
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
}
