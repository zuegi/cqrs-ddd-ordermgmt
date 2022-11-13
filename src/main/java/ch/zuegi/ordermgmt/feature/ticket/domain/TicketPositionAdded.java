package ch.zuegi.ordermgmt.feature.ticket.domain;

import ch.zuegi.ordermgmt.feature.ticket.domain.vo.TicketId;
import ch.zuegi.ordermgmt.feature.ticket.domain.vo.TicketPositionId;
import ch.zuegi.ordermgmt.feature.ticket.domain.vo.TradeItemId;
import ch.zuegi.ordermgmt.shared.DomainEvent;
import lombok.Value;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Value(staticConstructor = "eventOf")
public class TicketPositionAdded implements DomainEvent {

    TicketPositionId ticketPositionId;
    TicketId ticketId;
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
