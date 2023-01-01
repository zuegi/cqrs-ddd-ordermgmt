package ch.zuegi.ordermgmt.feature.ticket.domain;

import ch.zuegi.ordermgmt.feature.ticket.domain.command.CreateTicketCommand;
import ch.zuegi.ordermgmt.feature.ticket.domain.entity.TicketLifeCycleState;

import java.time.LocalDateTime;

public class TicketTestHelper {


    public static CreateTicketCommand createCommandForTest(LocalDateTime now) {
        return CreateTicketCommand.builder()
                .localDateTime(now)
                .ticketLifeCycleState(TicketLifeCycleState.TICKET_CREATED)
              /*  .createTicketPositionCommands(
                        Set.of(CreateTicketPositionCommand.builder()
                                .tradeItemId(new TradeItemId())
                                .menge(BigDecimal.TEN)
                                .build()
                        ))*/
                .build();
    }
}
