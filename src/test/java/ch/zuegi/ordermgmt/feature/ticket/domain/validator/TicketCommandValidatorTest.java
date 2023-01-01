package ch.zuegi.ordermgmt.feature.ticket.domain.validator;

import ch.zuegi.ordermgmt.feature.ticket.domain.Ticket;
import ch.zuegi.ordermgmt.feature.ticket.domain.TicketTestHelper;
import ch.zuegi.ordermgmt.feature.ticket.domain.command.CreateTicketCommand;
import ch.zuegi.ordermgmt.feature.ticket.domain.command.UpdateTicketLifecycleCommand;
import ch.zuegi.ordermgmt.feature.ticket.domain.entity.TicketLifeCycleState;
import ch.zuegi.ordermgmt.feature.ticket.domain.vo.TicketId;
import ch.zuegi.ordermgmt.shared.aggregateRoot.AggregateRootValidationMsg;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.lang.reflect.InvocationTargetException;
import java.time.LocalDateTime;

class TicketCommandValidatorTest {

    private TicketCommandValidator ticketCommandValidator;

    @BeforeEach
    void setup() {
        ticketCommandValidator = new TicketCommandValidator();
    }

    @Test
    void validate_create_ticket_comamnd() throws InvocationTargetException, IllegalAccessException {

        TicketId ticketId = new TicketId();
        Ticket ticket = new Ticket(ticketId);

        CreateTicketCommand command = TicketTestHelper.createCommandForTest(LocalDateTime.now());

        ticketCommandValidator.validate(ticket, command);
    }

    @Test
    void validate_create_ticket_command_is_null_throws_execption() {
        TicketId ticketId = new TicketId();
        Ticket ticket = new Ticket(ticketId);
        CreateTicketCommand command = null;
        // when
        Assertions.assertThatExceptionOfType(InvocationTargetException.class)
                .isThrownBy(() -> ticketCommandValidator.validate(ticket, command))
                .havingCause()
                .withMessage(AggregateRootValidationMsg.TICKET_COMMAND_MUST_NOT_BE_EMPTY);
    }


    @Test
    void validate_update_lifecycle_command_with_final_state_throw_execption() {
        // given
        TicketId ticketId = new TicketId();
        Ticket ticket = new Ticket(ticketId);
        LocalDateTime now = LocalDateTime.now();

        CreateTicketCommand commandForTest = TicketTestHelper.createCommandForTest(now);
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

        // then
        Assertions.assertThatExceptionOfType(InvocationTargetException.class)
                .isThrownBy(() -> ticketCommandValidator.validate(ticket, ticketCreated))
                .havingCause()
                .withMessage(AggregateRootValidationMsg.CURRENT_AGGREGATE_LIFECYCLE_STATE_IS_FINAL);

    }
}
