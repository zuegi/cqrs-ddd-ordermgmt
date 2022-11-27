package ch.zuegi.ordermgmt.feature.ticket.domain;


import ch.zuegi.ordermgmt.feature.ticket.domain.command.CreateTicketCommand;
import ch.zuegi.ordermgmt.feature.ticket.domain.command.CreateTicketPositionCommand;
import ch.zuegi.ordermgmt.feature.ticket.domain.entity.TicketLifeCycleState;
import ch.zuegi.ordermgmt.feature.ticket.domain.event.TicketCreated;
import ch.zuegi.ordermgmt.feature.ticket.domain.event.TicketLifecycleUpdated;
import ch.zuegi.ordermgmt.feature.ticket.domain.vo.TicketId;
import ch.zuegi.ordermgmt.feature.ticket.domain.vo.TradeItemId;
import ch.zuegi.ordermgmt.shared.aggregateRoot.AggregateRootValidationException;
import ch.zuegi.ordermgmt.shared.aggregateRoot.AggregateRootValidationMsg;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Set;

class TicketTest extends DomainTest {


    @Test
    void createTicketValid() {
        // given
        TicketId ticketId = new TicketId();
        // when
        Ticket.create(ticketId, createCommandForTest());
        // then
        expectedEvents(TicketCreated.class, 1);

    }

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
    void processTicketState() {
        // given
        TicketId ticketId = new TicketId();
        Ticket ticket = Ticket.create(ticketId, createCommandForTest());

        // when
        ticket.updateState(TicketLifeCycleState.TICKET_IN_PROCESSING);
        // when
        ticket.updateState(TicketLifeCycleState.TICKET_IS_PREPARED);
        // when
        ticket.updateState(TicketLifeCycleState.TICKET_PROCESSED);

        // when
        Assertions.assertThatExceptionOfType(AggregateRootValidationException.class)
                .isThrownBy(() -> ticket.updateState(TicketLifeCycleState.TICKET_CREATED))
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
