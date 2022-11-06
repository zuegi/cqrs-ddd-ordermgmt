package ch.zuegi.ordermgmt.feature.vaughn.ticket.domain;

import ch.zuegi.ordermgmt.feature.vaughn.ticket.domain.shared.DomainEvent;
import ch.zuegi.ordermgmt.feature.vaughn.ticket.domain.vo.TicketId;
import ch.zuegi.ordermgmt.feature.vaughn.ticket.domain.vo.TicketPositionId;
import ch.zuegi.ordermgmt.feature.vaughn.ticket.domain.vo.TradeItemId;
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
