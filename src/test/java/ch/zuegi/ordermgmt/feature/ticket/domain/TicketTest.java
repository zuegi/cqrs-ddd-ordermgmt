package ch.zuegi.ordermgmt.feature.ticket.domain;


import ch.zuegi.ordermgmt.feature.ticket.domain.command.CreateTicketCommand;
import ch.zuegi.ordermgmt.feature.ticket.domain.command.CreateTicketPositionCommand;
import ch.zuegi.ordermgmt.feature.ticket.domain.command.UpdateTicketLifecycleCommand;
import ch.zuegi.ordermgmt.feature.ticket.domain.entity.TicketLifeCycleState;
import ch.zuegi.ordermgmt.feature.ticket.domain.event.TicketCreated;
import ch.zuegi.ordermgmt.feature.ticket.domain.event.TicketLifecycleUpdated;
import ch.zuegi.ordermgmt.feature.ticket.domain.event.TicketPositionCreated;
import ch.zuegi.ordermgmt.feature.ticket.domain.vo.TicketId;
import ch.zuegi.ordermgmt.feature.ticket.domain.vo.TradeItemId;
import ch.zuegi.ordermgmt.shared.aggregateRoot.AggregateRootValidationException;
import ch.zuegi.ordermgmt.shared.aggregateRoot.AggregateRootValidationMsg;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;

class TicketTest extends DomainTest {

    // Zuerst immer die Fehlversuche erstellen
    // dann die validen Tests
    // Immer nur ein Assertion pro Test

    @Test
    void createTicketWithTicketIdIsNullInvalid() {
        TicketId ticketId = null;
        // when
        Assertions.assertThatExceptionOfType(AggregateRootValidationException.class)
                .isThrownBy(() -> Ticket.create(ticketId,  CreateTicketCommand.builder().build()))
                .withMessage(AggregateRootValidationMsg.AGGREGATE_ID_MUST_NOT_BE_NULL);
    }

    @Test
    void createTicketWithTicketCreateCommandIsNullInvalid() {
        TicketId ticketId = new TicketId();
        // when
        Assertions.assertThatExceptionOfType(AggregateRootValidationException.class)
                .isThrownBy(() -> Ticket.create(ticketId,  null))
                .withMessage(AggregateRootValidationMsg.AGGREGATE_COMMAND_MUST_NOT_BE_NULL);
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
        Assertions.assertThatExceptionOfType(AggregateRootValidationException.class)
                .isThrownBy(() -> Ticket.create(ticketId,  ticketCommand))
                .withMessage(AggregateRootValidationMsg.TICKET_COMMAND_TICKET_POSITION_SET_MUST_NOT_BE_EMPTY);

    }

    @Test
    void createTicketValid() {
        // given
        TicketId ticketId = new TicketId();
        // when
        Ticket.create(ticketId, createCommandForTest());
        // then
        expectedEvents(2);
        expectedEvents(TicketCreated.class, 1);
        expectedEvents(TicketPositionCreated.class, 1);

    }

    @Test
    void processTicketState() {
        // given
        TicketId ticketId = new TicketId();
        Ticket ticket = Ticket.create(ticketId, createCommandForTest());

        // when
        UpdateTicketLifecycleCommand ticketInProcessingCommand = UpdateTicketLifecycleCommand.builder().ticketLifeCycleState(TicketLifeCycleState.TICKET_IN_PROCESSING).localDateTime(LocalDateTime.now()).build();
        ticket.updateState(ticketInProcessingCommand);
        // when
        UpdateTicketLifecycleCommand ticketIsPreparedCommand = UpdateTicketLifecycleCommand.builder().ticketLifeCycleState(TicketLifeCycleState.TICKET_IS_PREPARED).localDateTime(LocalDateTime.now()).build();
        ticket.updateState(ticketIsPreparedCommand);
        // when
        UpdateTicketLifecycleCommand ticketProcessed = UpdateTicketLifecycleCommand.builder().ticketLifeCycleState(TicketLifeCycleState.TICKET_PROCESSED).localDateTime(LocalDateTime.now()).build();
        ticket.updateState(ticketProcessed);

        // when

        UpdateTicketLifecycleCommand ticketCreated = UpdateTicketLifecycleCommand.builder().ticketLifeCycleState(TicketLifeCycleState.TICKET_CREATED).localDateTime(LocalDateTime.now()).build();
        Assertions.assertThatExceptionOfType(AggregateRootValidationException.class)
                .isThrownBy(() -> ticket.updateState(ticketCreated))
                .withMessage(AggregateRootValidationMsg.CURRENT_AGGREGATE_LIFECYCLE_STATE_IS_FINAL);

        // then
        expectedEvents(TicketCreated.class, 1);
        expectedEvents(TicketLifecycleUpdated.class, 3);

        Assertions.assertThat(ticket.getTicketLifeCycleState()).isEqualTo(TicketLifeCycleState.TICKET_PROCESSED);

    }

    private CreateTicketCommand createCommandForTest() {
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
