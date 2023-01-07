package ch.zuegi.ordermgmt.feature.ticket.domain;

import ch.zuegi.ordermgmt.feature.ticket.domain.command.AddTicketPositionCommand;
import ch.zuegi.ordermgmt.feature.ticket.domain.command.CreateTicketCommand;
import ch.zuegi.ordermgmt.feature.ticket.domain.entity.TicketLifeCycleState;
import ch.zuegi.ordermgmt.feature.ticket.domain.event.TicketCreatedEvent;
import ch.zuegi.ordermgmt.feature.ticket.domain.event.TicketPositionAddedEvent;
import ch.zuegi.ordermgmt.feature.ticket.domain.event.TicketPositionRemovedEvent;
import ch.zuegi.ordermgmt.feature.ticket.domain.vo.TicketId;
import ch.zuegi.ordermgmt.feature.ticket.domain.vo.TicketPositionId;
import ch.zuegi.ordermgmt.feature.ticket.domain.vo.TradeItemId;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public class TicketTestHelper {


    public static CreateTicketCommand createCommandForTest(TicketId ticketId, LocalDateTime now) {
        return CreateTicketCommand.builder()
                .ticketId(ticketId)
                .localDateTime(now)
                .ticketLifeCycleState(TicketLifeCycleState.TICKET_CREATED)
                .build();
    }

    public static AddTicketPositionCommand getCreateTicketPositionCommand(TicketId ticketId, TicketPositionId ticketPositionId, TradeItemId tradeItemId, BigDecimal menge) {
        return AddTicketPositionCommand.builder()
                .ticketPositionId(ticketPositionId)
                .ticketId(ticketId)
                .menge(menge)
                .tradeItemId(tradeItemId)
                .build();
    }

    public static TicketCreatedEvent createTicketCreatedEvent(TicketId ticketId, LocalDateTime now) {
        return TicketCreatedEvent.builder()
                .ticketId(ticketId)
                .localDateTime(now)
                .lifeCycleState(TicketLifeCycleState.TICKET_CREATED)
                .build();
    }

    public static TicketPositionAddedEvent createTicketPositionAddedEvent(TicketId ticketId, TicketPositionId ticketPositionId, TradeItemId tradeItemId, BigDecimal menge) {
        return TicketPositionAddedEvent.builder()
                .ticketId(ticketId)
                .ticketPositionId(ticketPositionId)
                .tradeItemId(tradeItemId)
                .menge(menge)
                .build();
    }

    public static TicketPositionRemovedEvent createTicketPostionRemovedEvent(TicketId ticketId, TicketPositionId ticketPositionId) {
        return TicketPositionRemovedEvent.builder()
                .ticketId(ticketId)
                .ticketPositionId(ticketPositionId)
                .build();
    }
}
