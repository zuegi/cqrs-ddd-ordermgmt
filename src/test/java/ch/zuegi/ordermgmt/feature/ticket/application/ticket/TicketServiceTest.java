package ch.zuegi.ordermgmt.feature.ticket.application.ticket;

import ch.zuegi.ordermgmt.feature.ticket.application.AbstractIntegrationTest;
import ch.zuegi.ordermgmt.feature.ticket.domain.entity.Ticket;
import ch.zuegi.ordermgmt.feature.ticket.domain.entity.TicketPosition;
import ch.zuegi.ordermgmt.feature.ticket.domain.entity.TicketRepository;
import ch.zuegi.ordermgmt.feature.ticket.domain.TicketTestHelper;
import ch.zuegi.ordermgmt.feature.ticket.domain.command.AddTicketPositionCommand;
import ch.zuegi.ordermgmt.feature.ticket.domain.command.ConfirmTicketCommand;
import ch.zuegi.ordermgmt.feature.ticket.domain.command.CreateTicketCommand;
import ch.zuegi.ordermgmt.feature.ticket.domain.command.RemoveTicketPositionCommand;
import ch.zuegi.ordermgmt.feature.ticket.domain.entity.TicketLifeCycleState;
import ch.zuegi.ordermgmt.feature.ticket.domain.vo.TicketId;
import ch.zuegi.ordermgmt.feature.ticket.domain.vo.TicketPositionId;
import ch.zuegi.ordermgmt.feature.ticket.domain.vo.TradeItemId;
import org.assertj.core.api.Assertions;
import org.assertj.core.groups.Tuple;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.math.BigDecimal;
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
        TicketPositionId ticketPositionId = new TicketPositionId();
        AddTicketPositionCommand addTicketPositionCommand = TicketTestHelper.getCreateTicketPositionCommand(ticketId, ticketPositionId,  new TradeItemId(), BigDecimal.TEN);

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


    @Test
    void create_ticket_remove_ticket_position() {
        // given
        TicketId ticketId = new TicketId();
        CreateTicketCommand createTicktCommand = TicketTestHelper.createCommandForTest(ticketId, LocalDateTime.now());
        TicketPositionId ticketPositionId = new TicketPositionId();
        AddTicketPositionCommand addTicketPositionCommand = TicketTestHelper.getCreateTicketPositionCommand(ticketId, ticketPositionId, new TradeItemId(), BigDecimal.ONE);
        AddTicketPositionCommand anotherAddTicketPositionCommand = TicketTestHelper.getCreateTicketPositionCommand(ticketId, new TicketPositionId(), new TradeItemId(), BigDecimal.ONE);

        RemoveTicketPositionCommand removeTicketPositionCommand = RemoveTicketPositionCommand.builder()
                .ticketId(ticketId)
                .ticketPositionId(ticketPositionId).build();

        // when
        ticketService.createTicket(createTicktCommand);
        ticketService.addTicketPosition( addTicketPositionCommand);
        ticketService.addTicketPosition( anotherAddTicketPositionCommand);

        Optional<Ticket> byTicketId = ticketRepository.findByTicketId(ticketId);
        Assertions.assertThat(byTicketId.get().getTicketPositionList()).isNotNull().hasSize(2);

        // then
        ticketService.removeTicketPosition(removeTicketPositionCommand);
        Optional<Ticket> ticketWithRemovedPos = ticketRepository.findByTicketId(ticketId);

        Assertions.assertThat(ticketWithRemovedPos.get().getTicketPositionList()).isNotNull().hasSize(1)
                .extracting(TicketPosition::getTicketId,TicketPosition::id, TicketPosition::getTradeItemId, TicketPosition::getMenge)
                .contains(
                        Tuple.tuple(ticketId, ticketPositionId,  addTicketPositionCommand.getTradeItemId(), addTicketPositionCommand.getMenge())
                );
    }

    @Test
    void create_ticket_with_2_positions_and_confirm() {
        // given
        TicketId ticketId = new TicketId();
        CreateTicketCommand createTicktCommand = TicketTestHelper.createCommandForTest(ticketId, LocalDateTime.now());
        TicketPositionId ticketPositionId = new TicketPositionId();
        AddTicketPositionCommand addTicketPositionCommand = TicketTestHelper.getCreateTicketPositionCommand(ticketId, ticketPositionId, new TradeItemId(), BigDecimal.ONE);
        AddTicketPositionCommand anotherAddTicketPositionCommand = TicketTestHelper.getCreateTicketPositionCommand(ticketId, new TicketPositionId(), new TradeItemId(), BigDecimal.ONE);

        ticketService.createTicket(createTicktCommand);
        ticketService.addTicketPosition( addTicketPositionCommand);

        // when
        ConfirmTicketCommand confirmTicketCommand = ConfirmTicketCommand.builder().ticketId(ticketId).build();
        ticketService.confirmTicket(confirmTicketCommand);

        // then
        Ticket ticket = ticketRepository.findByTicketId(ticketId).get();
        Assertions.assertThat(ticket).isNotNull()
                .extracting(Ticket::getTicketLifeCycleState)
                .isEqualTo(TicketLifeCycleState.TICKET_CONFIRMED);
    }
}
