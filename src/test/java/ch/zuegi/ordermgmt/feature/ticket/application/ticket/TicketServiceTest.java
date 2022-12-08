package ch.zuegi.ordermgmt.feature.ticket.application.ticket;

import ch.zuegi.ordermgmt.feature.ticket.application.AbstractIntegrationTest;
import ch.zuegi.ordermgmt.feature.ticket.domain.Ticket;
import ch.zuegi.ordermgmt.feature.ticket.domain.TicketPosition;
import ch.zuegi.ordermgmt.feature.ticket.domain.TicketTestHelper;
import ch.zuegi.ordermgmt.feature.ticket.domain.command.CreateTicketCommand;
import ch.zuegi.ordermgmt.feature.ticket.domain.command.CreateTicketPositionCommand;
import ch.zuegi.ordermgmt.feature.ticket.domain.entity.TicketLifeCycleState;
import ch.zuegi.ordermgmt.feature.ticket.domain.vo.TicketId;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.time.LocalDateTime;

import static org.assertj.core.api.AssertionsForClassTypes.tuple;

class TicketServiceTest extends AbstractIntegrationTest {

    @Autowired
    TicketService ticketService;

    @Test
    void createValidTicketWithCommandHandler() {
        // given
        CreateTicketCommand commandForTest = TicketTestHelper.createCommandForTest(LocalDateTime.now());
        CreateTicketPositionCommand createTicketPositionCommand = commandForTest.getCreateTicketPositionCommands().stream().toList().get(0);

        TicketId ticketId = new TicketId();

        // when
        Ticket ticket = ticketService.createTicket(ticketId, commandForTest);

        // then
        Assertions.assertThat(ticket).isNotNull()
                .extracting(Ticket::id, Ticket::getTicketLifeCycleState)
                .contains(ticketId, TicketLifeCycleState.TICKET_CREATED);
        Assertions.assertThat(ticket.getTicketPositionSet())
                .hasSize(1)
                .extracting(TicketPosition::getTradeItemId, TicketPosition::getMenge)
                .contains(
                        tuple(createTicketPositionCommand.getTradeItemId(), createTicketPositionCommand.getMenge()));
    }

}
