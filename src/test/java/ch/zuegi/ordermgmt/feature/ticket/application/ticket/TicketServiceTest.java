package ch.zuegi.ordermgmt.feature.ticket.application.ticket;

import ch.zuegi.ordermgmt.feature.ticket.application.AbstractIntegrationTest;
import ch.zuegi.ordermgmt.feature.ticket.domain.Ticket;
import ch.zuegi.ordermgmt.feature.ticket.domain.TicketPosition;
import ch.zuegi.ordermgmt.feature.ticket.domain.TicketRepository;
import ch.zuegi.ordermgmt.feature.ticket.domain.TicketTestHelper;
import ch.zuegi.ordermgmt.feature.ticket.domain.command.AddTicketPositionCommand;
import ch.zuegi.ordermgmt.feature.ticket.domain.command.CreateTicketCommand;
import ch.zuegi.ordermgmt.feature.ticket.domain.entity.TicketLifeCycleState;
import ch.zuegi.ordermgmt.feature.ticket.domain.vo.TicketId;
import org.assertj.core.api.Assertions;
import org.assertj.core.groups.Tuple;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.time.LocalDateTime;
import java.util.Optional;

class TicketServiceTest extends AbstractIntegrationTest {

    @Autowired
    TicketService ticketService;

    @Autowired
    TicketRepository ticketRepository;

    @Test
    void create_ticket_valid() {
        // given
        TicketId ticketId = new TicketId();
        CreateTicketCommand commandForTest = TicketTestHelper.createCommandForTest(ticketId, LocalDateTime.now());

        // when
        ticketService.createTicket(commandForTest);

        // then
        Optional<Ticket> byTicketId = ticketRepository.findByTicketId(ticketId);
        Assertions.assertThat(byTicketId.isPresent()).isTrue();
        Assertions.assertThat(byTicketId.get()).isNotNull()
                .extracting(Ticket::id, Ticket::getTicketLifeCycleState)
                .contains(ticketId, TicketLifeCycleState.TICKET_CREATED);

    }

    @Test
    void create_ticket_add_ticket_position_valid() {
        // given
        TicketId ticketId = new TicketId();
        CreateTicketCommand createTicktCommand = TicketTestHelper.createCommandForTest(ticketId, LocalDateTime.now());
        AddTicketPositionCommand addTicketPositionCommand = TicketTestHelper.getCreateTicketPositionCommand(ticketId);

        // when
        ticketService.createTicket(createTicktCommand);
        ticketService.addTicketPosition( addTicketPositionCommand);

        // then
        Optional<Ticket> byTicketId = ticketRepository.findByTicketId(ticketId);
        Assertions.assertThat(byTicketId.isPresent()).isTrue();
        Assertions.assertThat(byTicketId.get().getTicketPositionList()).isNotNull().hasSize(1)
                .extracting(TicketPosition::getTicketId, TicketPosition::getTradeItemId, TicketPosition::getMenge)
                .contains(
                        Tuple.tuple(ticketId, addTicketPositionCommand.getTradeItemId(), addTicketPositionCommand.getMenge())
                );
    }

}
