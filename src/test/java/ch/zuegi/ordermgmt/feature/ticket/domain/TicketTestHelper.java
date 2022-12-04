package ch.zuegi.ordermgmt.feature.ticket.domain;

import ch.zuegi.ordermgmt.feature.ticket.domain.command.CreateTicketCommand;
import ch.zuegi.ordermgmt.feature.ticket.domain.command.CreateTicketPositionCommand;
import ch.zuegi.ordermgmt.feature.ticket.domain.entity.TicketLifeCycleState;
import ch.zuegi.ordermgmt.feature.ticket.domain.vo.TicketId;
import ch.zuegi.ordermgmt.feature.ticket.domain.vo.TradeItemId;
import org.assertj.core.api.Assertions;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Set;

public class TicketTestHelper {
    public static final String TICKET_ID = "12345";

    public static Ticket ticketForTest() {


        TicketId ticketId = new TicketId();
        return Ticket.create(ticketId, createCommandForTest());
    }

    public static CreateTicketCommand createCommandForTest() {
        return CreateTicketCommand.builder()
                .localDateTime(LocalDateTime.now())
                .ticketLifeCycleState(TicketLifeCycleState.TICKET_CREATED)
                .createTicketPositionCommands(
                        Set.of(CreateTicketPositionCommand.builder()
                                .tradeItemId(new TradeItemId())
                                .menge(BigDecimal.TEN)
                                .build()
                        )
                ).build();
    }
}
