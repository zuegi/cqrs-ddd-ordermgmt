package ch.zuegi.ordermgmt.feature.vaughn.ticket.domain;

import ch.zuegi.ordermgmt.feature.vaughn.ticket.domain.shared.DomainEvent;
import ch.zuegi.ordermgmt.feature.vaughn.ticket.domain.vo.TicketId;
import lombok.Value;

import java.time.LocalDateTime;

@Value(staticConstructor = "eventOf")
public class TicketCreated implements DomainEvent {
    TicketId ticketId;
    LocalDateTime localDateTime;

    @Override
    public int eventVersion() {
        return 0;
    }

    @Override
    public LocalDateTime occurredOn() {
        return localDateTime;
    }
}
