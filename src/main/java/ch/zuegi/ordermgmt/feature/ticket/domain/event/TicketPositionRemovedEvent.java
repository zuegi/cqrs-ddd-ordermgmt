package ch.zuegi.ordermgmt.feature.ticket.domain.event;

import ch.zuegi.ordermgmt.feature.ticket.domain.vo.TicketId;
import ch.zuegi.ordermgmt.feature.ticket.domain.vo.TicketPositionId;
import ch.zuegi.ordermgmt.feature.ticket.domain.vo.TradeItemId;
import ch.zuegi.ordermgmt.shared.DomainEvent;
import lombok.Builder;
import lombok.Getter;
import lombok.ToString;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Builder
@Getter
@ToString
public class TicketPositionRemovedEvent extends DomainEvent<TicketPositionRemovedEvent, TicketId> {

    TicketPositionId ticketPositionId;
    TicketId ticketId;
    TradeItemId tradeItemId;
    BigDecimal menge;


    @Override
    public TicketPositionRemovedEvent getEvent() {
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
