package ch.zuegi.ordermgmt.feature.ticket.domain.event;

import ch.zuegi.ordermgmt.feature.ticket.domain.entity.TicketLifeCycleState;
import ch.zuegi.ordermgmt.feature.ticket.domain.vo.TicketNumber;
import ch.zuegi.ordermgmt.shared.DomainEvent;
import lombok.Value;

import java.time.LocalDateTime;
import java.util.Set;

@Value(staticConstructor = "eventOf")
public class TicketSaveEvent  implements DomainEvent {

    TicketNumber ticketNumber;
    LocalDateTime localDateTime;
    TicketLifeCycleState lifeCycleState;
    Set<TicketPositionAddEvent> ticketPositionAddedSet;

    @Override
    public int eventVersion() {
        return 0;
    }

    @Override
    public LocalDateTime occurredOn() {
        return null;
    }
}
