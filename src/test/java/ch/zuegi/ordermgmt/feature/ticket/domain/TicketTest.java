package ch.zuegi.ordermgmt.feature.ticket.domain;


import ch.zuegi.ordermgmt.feature.ticket.domain.command.CreateTicketCommand;
import ch.zuegi.ordermgmt.feature.ticket.domain.command.UpdateTicketLifecycleCommand;
import ch.zuegi.ordermgmt.feature.ticket.domain.entity.Ticket;
import ch.zuegi.ordermgmt.feature.ticket.domain.entity.TicketLifeCycleState;
import ch.zuegi.ordermgmt.feature.ticket.domain.entity.TicketPosition;
import ch.zuegi.ordermgmt.feature.ticket.domain.event.TicketConfirmedEvent;
import ch.zuegi.ordermgmt.feature.ticket.domain.event.TicketCreatedEvent;
import ch.zuegi.ordermgmt.feature.ticket.domain.event.TicketPositionAddedEvent;
import ch.zuegi.ordermgmt.feature.ticket.domain.vo.TicketId;
import ch.zuegi.ordermgmt.feature.ticket.domain.vo.TicketPositionId;
import ch.zuegi.ordermgmt.feature.ticket.domain.vo.TradeItemId;
import ch.zuegi.ordermgmt.shared.DomainEvent;
import ch.zuegi.ordermgmt.shared.aggregateRoot.AggregateRootValidationException;
import ch.zuegi.ordermgmt.shared.aggregateRoot.AggregateRootValidationMsg;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.AssertionsForClassTypes.tuple;

class TicketTest {

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
    void validateTicketWithTicketCreateCommandIsNullInvalid() {
        TicketId ticketId = new TicketId();
        Ticket ticket = new Ticket(ticketId);
        CreateTicketCommand command = null;
        // when
        Assertions.assertThatExceptionOfType(AggregateRootValidationException.class)
                .isThrownBy(() -> ticket.validate(command))
                .withMessage(AggregateRootValidationMsg.TICKET_COMMAND_MUST_NOT_BE_EMPTY);
    }

    @Disabled
    @Test
    void validate_ticket_with_void_set_of_ticketPosition_invalid() {
        // given ticketCommand with void Set of TicketPosition
        CreateTicketCommand ticketCommand = CreateTicketCommand.builder()
                .localDateTime(LocalDateTime.now())
                .ticketLifeCycleState(TicketLifeCycleState.TICKET_CREATED)
//                .createTicketPositionCommands(new HashSet<>())
                .build();

        TicketId ticketId = new TicketId();
        Ticket ticket = new Ticket(ticketId);
        Assertions.assertThatExceptionOfType(AggregateRootValidationException.class)
                .isThrownBy(() -> ticket.validate(ticketCommand))
                .withMessage(AggregateRootValidationMsg.TICKET_COMMAND_TICKET_POSITION_SET_MUST_NOT_BE_EMPTY);

    }

    @Test
    void createTicketValid() {
        // given
        TicketId ticketId = new TicketId();
        Ticket ticket = new Ticket(ticketId);
        LocalDateTime now = LocalDateTime.now();

        // when
        CreateTicketCommand commandForTest = TicketTestHelper.createCommandForTest(ticketId, now);

        ticket.handle(commandForTest);
        // then
        Assertions.assertThat(ticket).isNotNull()
                .extracting(Ticket::id, Ticket::getTicketLifeCycleState)
                .contains(ticketId, TicketLifeCycleState.TICKET_CREATED);

    }

    @Test
    void process_and_validate_ticket_state() {
        // given
        TicketId ticketId = new TicketId();
        Ticket ticket = new Ticket(ticketId);
        LocalDateTime now = LocalDateTime.now();

        CreateTicketCommand commandForTest = TicketTestHelper.createCommandForTest(ticketId, now);
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
        Assertions.assertThatExceptionOfType(AggregateRootValidationException.class)
                .isThrownBy(() -> ticket.validate(ticketCreated))
                .withMessage(AggregateRootValidationMsg.CURRENT_AGGREGATE_LIFECYCLE_STATE_IS_FINAL);

        // then

        Assertions.assertThat(ticket.getTicketLifeCycleState()).isEqualTo(TicketLifeCycleState.TICKET_PROCESSED);
    }

    @Test
    void load_ticket_with_ticket_created_event() {
        // given
        List<DomainEvent<?, TicketId>> ticketDomainEvents = new ArrayList<>();
        TicketId ticketId = new TicketId();
        LocalDateTime now = LocalDateTime.now();
        TicketCreatedEvent ticketCreatedEvent = TicketTestHelper.createTicketCreatedEvent(ticketId, now);
        ticketDomainEvents.add(ticketCreatedEvent);

        Ticket ticket = new Ticket(ticketId);
        // when
        ticket.aggregateEvents(ticketDomainEvents);

        // then
        Assertions.assertThat(ticket).isNotNull()
                .extracting(Ticket::id, Ticket::getLocalDateTime, Ticket::getTicketLifeCycleState, Ticket::getTicketPositionList)
                .contains(ticketId, now, TicketLifeCycleState.TICKET_CREATED, new ArrayList<>());
    }


    @Test
    void load_ticket_with_ticket_position_added_event() {

        // given
        List<DomainEvent<?, TicketId>> ticketDomainEvents = new ArrayList<>();
        TicketId ticketId = new TicketId();
        LocalDateTime now = LocalDateTime.now();
        TicketCreatedEvent ticketCreatedEvent = TicketTestHelper.createTicketCreatedEvent(ticketId, now);
        ticketDomainEvents.add(ticketCreatedEvent);

        TicketPositionId firstTicketPositionId = new TicketPositionId();
        TradeItemId firstTradeItemId = new TradeItemId();
        BigDecimal firstMenge = BigDecimal.ONE;
        TicketPositionAddedEvent firstTicketPositionAddedEvent = TicketTestHelper.createTicketPositionAddedEvent(ticketId, firstTicketPositionId, firstTradeItemId, firstMenge);
        ticketDomainEvents.add(firstTicketPositionAddedEvent);

        TicketPositionId seondTicketPositionId = new TicketPositionId();
        TradeItemId secondTradeItemId = new TradeItemId();
        BigDecimal secondMenge = BigDecimal.ONE;
        TicketPositionAddedEvent secondTicketPositionAddedEvent = TicketTestHelper.createTicketPositionAddedEvent(ticketId, seondTicketPositionId, secondTradeItemId, secondMenge);
        ticketDomainEvents.add(secondTicketPositionAddedEvent);

        Ticket ticket = new Ticket(ticketId);
        // when
        ticket.aggregateEvents(ticketDomainEvents);

        // then
        Assertions.assertThat(ticket).isNotNull();
        Assertions.assertThat(ticket.getTicketPositionList())
                .hasSize(2)
                .extracting(TicketPosition::getTicketId, TicketPosition::id, TicketPosition::getTradeItemId, TicketPosition::getMenge)
                .containsExactlyInAnyOrder(
                        tuple(ticketId, firstTicketPositionId, firstTradeItemId, firstMenge),
                        tuple(ticketId, seondTicketPositionId, secondTradeItemId, secondMenge)
                );
    }

    @Test
    void load_ticket_with_ticket_position_added_event_and_remove_one_position() {
        // given
        TicketId ticketId = new TicketId();
        LocalDateTime now = LocalDateTime.now();
        TicketPositionId firstTicketPositionId = new TicketPositionId();
        TradeItemId firstTradeItemId = new TradeItemId();
        BigDecimal firstMenge = BigDecimal.TEN;
        TicketPositionId secondTicketPositionId = new TicketPositionId();
        TradeItemId secondTradeItemId = new TradeItemId();
        BigDecimal secondMenge = BigDecimal.ONE;

        List<DomainEvent<?, TicketId>> ticketDomainEvents = TicketTestHelper.createTicketWithTwoPositionEventss(ticketId, now, firstTicketPositionId, firstTradeItemId, firstMenge, secondTicketPositionId, secondTradeItemId, secondMenge);

        Ticket ticket = new Ticket(ticketId);
        ticket.aggregateEvents(ticketDomainEvents);
        // then
        Assertions.assertThat(ticket.getTicketPositionList())
                .hasSize(1)
                .extracting(TicketPosition::getTicketId, TicketPosition::id, TicketPosition::getTradeItemId, TicketPosition::getMenge)
                .contains(
                        tuple(ticketId, secondTicketPositionId, secondTradeItemId, secondMenge)
                );
    }


    @Test
    void load_ticket_with_2_position_and_confirm() {
        // given
        TicketId ticketId = new TicketId();
        LocalDateTime now = LocalDateTime.now();
        TicketPositionId firstTicketPositionId = new TicketPositionId();
        TradeItemId firstTradeItemId = new TradeItemId();
        BigDecimal firstMenge = BigDecimal.TEN;
        TicketPositionId secondTicketPositionId = new TicketPositionId();
        TradeItemId secondTradeItemId = new TradeItemId();
        BigDecimal secondMenge = BigDecimal.ONE;

        List<DomainEvent<?, TicketId>> domainEvents = TicketTestHelper.createTicketWithTwoPositionEventss(ticketId, now, firstTicketPositionId, firstTradeItemId, firstMenge, secondTicketPositionId, secondTradeItemId, secondMenge);

        // when
        TicketConfirmedEvent ticketConfirmedEvent = TicketConfirmedEvent.builder()
                .ticketId(ticketId).ticketLifeCycleState(TicketLifeCycleState.TICKET_CONFIRMED).build();
        domainEvents.add(ticketConfirmedEvent);

        Ticket ticket = new Ticket(ticketId);
        ticket.aggregateEvents(domainEvents);

        // then
        Assertions.assertThat(ticket)
                .isNotNull()
                .extracting(Ticket::getTicketLifeCycleState)
                .isEqualTo(TicketLifeCycleState.TICKET_CONFIRMED);
    }
}
