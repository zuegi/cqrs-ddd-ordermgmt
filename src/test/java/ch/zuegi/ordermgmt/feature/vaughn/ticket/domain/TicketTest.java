package ch.zuegi.ordermgmt.feature.vaughn.ticket.domain;


import ch.zuegi.ordermgmt.feature.vaughn.ticket.domain.vo.TicketId;
import ch.zuegi.ordermgmt.feature.vaughn.ticket.domain.vo.TicketPositionId;
import ch.zuegi.ordermgmt.feature.vaughn.ticket.domain.vo.TradeItemId;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;

class TicketTest extends DomainTest {


    @Test
    void createTicket() {
        TicketId ticketId = new TicketId("12345");
        // ins Ticket den DomainEventPublisher
        Ticket ticket = new Ticket(ticketId);

        Assertions.assertThat(ticket).isNotNull()
                .extracting(Ticket::id)
                .isEqualTo(ticketId);


        expectedEvents(1);
        expectedEvent(TicketCreated.class);
    }

    @Test
    void addTicketPositionToTicket() {
        Ticket ticket = ticketForTest();
        TicketPositionId ticketPositionId = new TicketPositionId();
        TradeItemId tradeItemId = new TradeItemId();
        TicketPosition ticketPosition = ticket.addTicketPosition(ticketPositionId, ticket.id(), tradeItemId, BigDecimal.TEN);
        Assertions.assertThat(ticketPosition).isNotNull()
                .extracting(TicketPosition::id, TicketPosition::getTicketId, TicketPosition::getTradeItemId, TicketPosition::getMenge)
                .contains(ticketPositionId, ticket.id(), tradeItemId, BigDecimal.TEN);

        // und was nun? gibt die plannedTicketPosition den Event TicketPositionPlanned zurück?
        expectedEvents(2);
        expectedEvent(TicketCreated.class);
        expectedEvent(TicketPositionAdded.class);

    }

    private Ticket ticketForTest() {
        TicketId ticketId = new TicketId("12345");
        Ticket ticket = new Ticket(ticketId);

        Assertions.assertThat(ticket).isNotNull()
                .extracting(Ticket::id)
                .isEqualTo(ticketId);

        return ticket;
    }
}
