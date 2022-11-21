package ch.zuegi.ordermgmt.feature.ticket.domain;


import ch.zuegi.ordermgmt.feature.ticket.domain.command.SaveTicketCommand;
import ch.zuegi.ordermgmt.feature.ticket.domain.entity.TicketLifeCycleState;
import ch.zuegi.ordermgmt.feature.ticket.domain.event.TicketPositionAddEvent;
import ch.zuegi.ordermgmt.feature.ticket.domain.event.TicketSaveEvent;
import ch.zuegi.ordermgmt.feature.ticket.domain.vo.TicketNumber;
import ch.zuegi.ordermgmt.shared.aggregateRoot.AggregateRootValidationException;
import ch.zuegi.ordermgmt.shared.aggregateRoot.AggregateRootValidationMsg;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;

import static ch.zuegi.ordermgmt.feature.ticket.domain.TicketTestHelper.TICKET_ID;

class TicketTest extends DomainTest {


    @Test
    void createTicket() {
        // given
        TicketNumber ticketNumber = new TicketNumber(TICKET_ID);
        Ticket ticket = new Ticket(ticketNumber);
        LocalDateTime now = LocalDateTime.now();

        Set<TicketPositionAddEvent> ticketPositionAddedSet = new HashSet<>();

        SaveTicketCommand saveTicketCommand = SaveTicketCommand.commandOf(now, TicketLifeCycleState.TICKET_CREATED, ticketPositionAddedSet);

        // when
        ticket.handle(saveTicketCommand);

        // then
        expectedEvents(1);
        expectedEvent(TicketSaveEvent.class);
        expectedEvent(TicketPositionAddEvent.class, 0);

    }

    @Test
    void createTicketInvalid() {
        TicketNumber ticketNumber = null;
        // when
        Assertions.assertThatExceptionOfType(AggregateRootValidationException.class)
                .isThrownBy(() -> new Ticket(ticketNumber))
                .withMessage(AggregateRootValidationMsg.AGGREGATE_ID_MUST_NOT_BE_NULL);
    }



}
