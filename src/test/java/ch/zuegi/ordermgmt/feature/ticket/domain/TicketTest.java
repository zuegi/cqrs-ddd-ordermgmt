package ch.zuegi.ordermgmt.feature.ticket.domain;


import ch.zuegi.ordermgmt.feature.ticket.domain.command.CreateTicketCommand;
import ch.zuegi.ordermgmt.feature.ticket.domain.command.CreateTicketPositionCommand;
import ch.zuegi.ordermgmt.feature.ticket.domain.command.UpdateTicketLifecycleCommand;
import ch.zuegi.ordermgmt.feature.ticket.domain.entity.TicketLifeCycleState;
import ch.zuegi.ordermgmt.feature.ticket.domain.vo.TicketId;
import ch.zuegi.ordermgmt.shared.aggregateRoot.AggregateRootValidationException;
import ch.zuegi.ordermgmt.shared.aggregateRoot.AggregateRootValidationMsg;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;

import java.lang.reflect.InvocationTargetException;
import java.time.LocalDateTime;
import java.util.HashSet;

import static org.assertj.core.api.AssertionsForClassTypes.tuple;

class TicketTest /*extends DomainTest*/ {

    // Zuerst immer die Fehlversuche erstellen
    // dann die validen Tests
    // Immer nur ein Assertion pro Test

    @Test
    void createTicketWithTicketIdIsNullInvalid() {
        TicketId ticketId = null;
        // when
        Assertions.assertThatExceptionOfType(AggregateRootValidationException.class)
                .isThrownBy(() -> new Ticket(ticketId))
                .withMessage(AggregateRootValidationMsg.AGGREGATE_ID_MUST_NOT_BE_NULL);
    }

    @Test
    void createTicketWithTicketCreateCommandIsNullInvalid() {
        TicketId ticketId = new TicketId();
        Ticket ticket = new Ticket(ticketId);
        CreateTicketCommand command = null;
        // when
        Assertions.assertThatExceptionOfType(InvocationTargetException.class)
                .isThrownBy(() -> ticket.handle(command))
                .havingCause()
                .withMessage(AggregateRootValidationMsg.TICKET_COMMAND_MUST_NOT_BE_EMPTY);
    }

    @Test
    void create_ticket_with_void_set_of_ticketPosition_invalid() {
        // given ticketCommand with void Set of TicketPosition
        CreateTicketCommand ticketCommand = CreateTicketCommand.builder()
                .localDateTime(LocalDateTime.now())
                .ticketLifeCycleState(TicketLifeCycleState.TICKET_CREATED)
                .createTicketPositionCommands(new HashSet<>())
                .build();

        TicketId ticketId = new TicketId();
        Ticket ticket = new Ticket(ticketId);
        Assertions.assertThatExceptionOfType(InvocationTargetException.class)
                .isThrownBy(() -> ticket.handle(ticketCommand))
                .havingCause()
                .withMessage(AggregateRootValidationMsg.TICKET_COMMAND_TICKET_POSITION_SET_MUST_NOT_BE_EMPTY);

    }

    @Test
    void createTicketValid() throws InvocationTargetException, IllegalAccessException {
        // given
        TicketId ticketId = new TicketId();
        Ticket ticket = new Ticket(ticketId);
        LocalDateTime now = LocalDateTime.now();

        // when
        CreateTicketCommand commandForTest = TicketTestHelper.createCommandForTest(now);
        CreateTicketPositionCommand createTicketPositionCommand = commandForTest.getCreateTicketPositionCommands().stream().toList().get(0);
        ticket.handle(commandForTest);
        // then
        Assertions.assertThat(ticket).isNotNull()
                .extracting(Ticket::id, Ticket::getTicketLifeCycleState)
                .contains(ticketId, TicketLifeCycleState.TICKET_CREATED);
        Assertions.assertThat(ticket.getTicketPositionSet())
                .hasSize(1)
                .extracting(TicketPosition::getTradeItemId, TicketPosition::getMenge)
                .contains(
                        tuple(createTicketPositionCommand.getTradeItemId(), createTicketPositionCommand.getMenge())
                );
    }

    @Test
    void processTicketState() throws InvocationTargetException, IllegalAccessException {
        // given
        TicketId ticketId = new TicketId();
        Ticket ticket = new Ticket(ticketId);
        LocalDateTime now = LocalDateTime.now();

        CreateTicketCommand commandForTest = TicketTestHelper.createCommandForTest(now);
        CreateTicketPositionCommand createTicketPositionCommand = commandForTest.getCreateTicketPositionCommands().stream().toList().get(0);
        ticket.handle(commandForTest);

        // when
        UpdateTicketLifecycleCommand ticketInProcessingCommand = UpdateTicketLifecycleCommand.builder().ticketLifeCycleState(TicketLifeCycleState.TICKET_IN_PROCESSING).localDateTime(LocalDateTime.now()).build();
        ticket.handle(ticketInProcessingCommand);
        // when
        UpdateTicketLifecycleCommand ticketIsPreparedCommand = UpdateTicketLifecycleCommand.builder().ticketLifeCycleState(TicketLifeCycleState.TICKET_IS_PREPARED).localDateTime(LocalDateTime.now()).build();
        ticket.handle(ticketIsPreparedCommand);
        // when
        UpdateTicketLifecycleCommand ticketProcessed = UpdateTicketLifecycleCommand.builder().ticketLifeCycleState(TicketLifeCycleState.TICKET_PROCESSED).localDateTime(LocalDateTime.now()).build();
        ticket.handle(ticketProcessed);

        // when

        UpdateTicketLifecycleCommand ticketCreated = UpdateTicketLifecycleCommand.builder().ticketLifeCycleState(TicketLifeCycleState.TICKET_CREATED).localDateTime(LocalDateTime.now()).build();
        Assertions.assertThatExceptionOfType(InvocationTargetException.class)
                .isThrownBy(() -> ticket.handle(ticketCreated))
                .havingCause()
                .withMessage(AggregateRootValidationMsg.CURRENT_AGGREGATE_LIFECYCLE_STATE_IS_FINAL);

        // then

        Assertions.assertThat(ticket.getTicketLifeCycleState()).isEqualTo(TicketLifeCycleState.TICKET_PROCESSED);
    }


}
