package ch.zuegi.ordermgmt.feature.ticket.domain.event;

import ch.zuegi.ordermgmt.feature.ticket.domain.vo.TicketNumber;
import ch.zuegi.ordermgmt.feature.ticket.domain.vo.TicketPositionNumber;
import ch.zuegi.ordermgmt.feature.ticket.domain.vo.TradeItemId;
import ch.zuegi.ordermgmt.shared.DomainEvent;
import lombok.Value;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Value(staticConstructor = "eventOf")
public class TicketPositionAddEvent implements DomainEvent {

    TicketPositionNumber ticketPositionId;
    TicketNumber ticketNumber;
    TradeItemId tradeItemId;
    BigDecimal menge;

    @Override
    public int eventVersion() {
        return 0;
    }

    @Override
    public LocalDateTime occurredOn() {
        return LocalDateTime.now();
    }
}
