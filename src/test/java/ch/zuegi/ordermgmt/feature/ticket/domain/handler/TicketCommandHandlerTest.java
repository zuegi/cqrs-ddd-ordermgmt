package ch.zuegi.ordermgmt.feature.ticket.domain.handler;

import ch.zuegi.ordermgmt.feature.ticket.domain.InMemoryTicketRepositoryImpl;
import ch.zuegi.ordermgmt.feature.ticket.domain.TicketRepository;
import ch.zuegi.ordermgmt.feature.ticket.domain.TicketTestHelper;
import ch.zuegi.ordermgmt.feature.ticket.domain.command.CreateTicketCommand;
import ch.zuegi.ordermgmt.feature.ticket.domain.command.AddTicketPositionCommand;
import ch.zuegi.ordermgmt.feature.ticket.domain.command.UpdateTicketLifecycleCommand;
import ch.zuegi.ordermgmt.feature.ticket.domain.entity.TicketLifeCycleState;
import ch.zuegi.ordermgmt.feature.ticket.domain.event.TicketCreatedEvent;
import ch.zuegi.ordermgmt.feature.ticket.domain.event.TicketPositionAddedEvent;
import ch.zuegi.ordermgmt.feature.ticket.domain.validator.TicketCommandValidator;
import ch.zuegi.ordermgmt.feature.ticket.domain.vo.TicketId;
import ch.zuegi.ordermgmt.feature.ticket.domain.vo.TradeItemId;
import ch.zuegi.ordermgmt.shared.aggregateRoot.AggregateRootValidationException;
import ch.zuegi.ordermgmt.shared.aggregateRoot.AggregateRootValidationMsg;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.context.ApplicationEventPublisher;

import java.lang.reflect.InvocationTargetException;
import java.math.BigDecimal;
import java.time.LocalDateTime;

class TicketCommandHandlerTest {

    private TicketCommandHandler ticketCommandHandler;

    private final ApplicationEventPublisher applicationEventPublisher = Mockito.mock(ApplicationEventPublisher.class);

    @BeforeEach
    void setup() {
        Mockito.reset(applicationEventPublisher);
        TicketRepository ticketRepository = new InMemoryTicketRepositoryImpl();
        TicketCommandValidator ticketCommandValidator = new TicketCommandValidator();
        ticketCommandHandler = new TicketCommandHandler(ticketCommandValidator, applicationEventPublisher, ticketRepository);
    }

    @Test
    void validate_create_ticket_comamnd() throws InvocationTargetException, IllegalAccessException {

        // given
        TicketId ticketId = new TicketId();
        CreateTicketCommand command = TicketTestHelper.createCommandForTest(ticketId, LocalDateTime.now());

        // when
        ticketCommandHandler.handle(ticketId, command);

        Mockito.verify(applicationEventPublisher, Mockito.times(1)).publishEvent(Mockito.any(TicketCreatedEvent.class));

    }


    @Test
    void handle_wrong_command_invalid() {

        TicketId ticketId = new TicketId();
        TestCommand testCommand = TestCommand.builder().build();

        Assertions.assertThatExceptionOfType(AggregateRootValidationException.class)
                .isThrownBy(() -> ticketCommandHandler.handle(ticketId, testCommand))
                .withMessage(AggregateRootValidationMsg.TICKET_HANDLE_COMMAND_INVALID);

    }

    @Test
    void validate_create_ticket_command_is_null_throws_execption() {
        TicketId ticketId = new TicketId();
        CreateTicketCommand command = null;
        // when
        Assertions.assertThatExceptionOfType(AggregateRootValidationException.class)
                .isThrownBy(() -> ticketCommandHandler.handle(ticketId, command))
                .withMessage(AggregateRootValidationMsg.TICKET_HANDLE_COMMAND_INVALID);
    }


    @Disabled("Muss fertig implementiert werden")
    @Test
    void validate_update_lifecycle_command_with_final_state_throw_execption() throws InvocationTargetException, IllegalAccessException {
        // given
        TicketId ticketId = new TicketId();

        LocalDateTime now = LocalDateTime.now();

        CreateTicketCommand commandForTest = TicketTestHelper.createCommandForTest(ticketId, now);
        ticketCommandHandler.handle(ticketId, commandForTest);

        // when
        UpdateTicketLifecycleCommand ticketInProcessingCommand = UpdateTicketLifecycleCommand.builder().ticketLifeCycleState(TicketLifeCycleState.TICKET_IN_PROCESSING).localDateTime(LocalDateTime.now()).build();
        ticketCommandHandler.handle(ticketId, ticketInProcessingCommand);
        // when
        UpdateTicketLifecycleCommand ticketIsPreparedCommand = UpdateTicketLifecycleCommand.builder().ticketLifeCycleState(TicketLifeCycleState.TICKET_IS_PREPARED).localDateTime(LocalDateTime.now()).build();
        ticketCommandHandler.handle(ticketId, ticketIsPreparedCommand);
        // when
        UpdateTicketLifecycleCommand ticketProcessed = UpdateTicketLifecycleCommand.builder().ticketLifeCycleState(TicketLifeCycleState.TICKET_PROCESSED).localDateTime(LocalDateTime.now()).build();
        ticketCommandHandler.handle(ticketId, ticketProcessed);

        // when
        UpdateTicketLifecycleCommand ticketCreated = UpdateTicketLifecycleCommand.builder().ticketLifeCycleState(TicketLifeCycleState.TICKET_CREATED).localDateTime(LocalDateTime.now()).build();

        // then
        Assertions.assertThatExceptionOfType(InvocationTargetException.class)
                .isThrownBy(() -> ticketCommandHandler.handle(ticketId, ticketCreated))
                .havingCause()
                .withMessage(AggregateRootValidationMsg.CURRENT_AGGREGATE_LIFECYCLE_STATE_IS_FINAL);

    }

    @Test
    void add_ticket_position_ticket_valid() throws InvocationTargetException, IllegalAccessException {
        // given
        TicketId ticketId = new TicketId();
        CreateTicketCommand command = TicketTestHelper.createCommandForTest(ticketId, LocalDateTime.now());
        ticketCommandHandler.handle(ticketId, command);

        //
        AddTicketPositionCommand addTicketPosition = AddTicketPositionCommand.builder()
                .ticketId(ticketId)
                .tradeItemId(new TradeItemId())
                .menge(BigDecimal.TEN)
                .build();

        // when
        ticketCommandHandler.handle(ticketId, addTicketPosition);

        // then
        Mockito.verify(applicationEventPublisher, Mockito.times(1)).publishEvent(Mockito.any(TicketPositionAddedEvent.class));
    }
}
