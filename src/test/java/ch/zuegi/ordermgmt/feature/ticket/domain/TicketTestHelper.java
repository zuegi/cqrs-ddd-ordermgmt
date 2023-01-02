package ch.zuegi.ordermgmt.feature.ticket.domain;

import ch.zuegi.ordermgmt.feature.ticket.domain.command.AddTicketPositionCommand;
import ch.zuegi.ordermgmt.feature.ticket.domain.command.CreateTicketCommand;
import ch.zuegi.ordermgmt.feature.ticket.domain.entity.TicketLifeCycleState;
import ch.zuegi.ordermgmt.feature.ticket.domain.vo.TicketId;
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

    public static AddTicketPositionCommand getCreateTicketPositionCommand(TicketId ticketId) {
        return AddTicketPositionCommand.builder()
                .ticketId(ticketId)
                .menge(BigDecimal.TEN)
                .tradeItemId(new TradeItemId())
                .build();
    }
}
