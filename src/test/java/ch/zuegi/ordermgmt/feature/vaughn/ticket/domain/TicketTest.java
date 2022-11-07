package ch.zuegi.ordermgmt.feature.vaughn.ticket.domain;


import ch.zuegi.ordermgmt.feature.ticket.domain.Order;
import ch.zuegi.ordermgmt.feature.vaughn.ticket.domain.vo.TicketId;
import ch.zuegi.ordermgmt.feature.vaughn.ticket.domain.vo.TicketPositionId;
import ch.zuegi.ordermgmt.feature.vaughn.ticket.domain.vo.TradeItemId;
import ch.zuegi.ordermgmt.shared.aggregateRoot.AggregateRootValidationException;
import ch.zuegi.ordermgmt.shared.aggregateRoot.AggregateRootValidationMsg;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;

class TicketTest extends DomainTest {


    public static final String TICKET_ID = "12345";

    @Test
    void createTicket() {
        // given
        TicketId ticketId = new TicketId(TICKET_ID);
        // when
        Ticket ticket = new Ticket(ticketId);

        // then
        Assertions.assertThat(ticket).isNotNull()
                .extracting(Ticket::id)
                .isEqualTo(ticketId);

        // then
        expectedEvents(1);
        expectedEvent(TicketCreated.class);
    }

    @Test
    void createTicketInvalid() {
        TicketId ticketId = null;
        // when
        Assertions.assertThatExceptionOfType(AggregateRootValidationException.class)
                .isThrownBy(() -> new Ticket(ticketId))
                .withMessage(AggregateRootValidationMsg.AGGREGATE_ID_MUST_NOT_BE_NULL);
    }

    @Test
    void addTicketPositionToTicket() {
        // given
        Ticket ticket = ticketForTest();
        TicketPositionId ticketPositionId = new TicketPositionId(TICKET_ID);
        TradeItemId tradeItemId = new TradeItemId();
        // when
        TicketPosition ticketPosition = ticket.addTicketPosition(ticketPositionId, ticket.id(), tradeItemId, BigDecimal.TEN);

        // then
        Assertions.assertThat(ticketPosition).isNotNull()
                .extracting(TicketPosition::id, TicketPosition::getTicketId, TicketPosition::getTradeItemId, TicketPosition::getMenge)
                .contains(ticketPositionId, ticket.id(), tradeItemId, BigDecimal.TEN);

        // then
        expectedEvents(2);
        expectedEvent(TicketCreated.class);
        expectedEvent(TicketPositionAdded.class);

    }

    private Ticket ticketForTest() {
        TicketId ticketId = new TicketId(TICKET_ID);
        Ticket ticket = new Ticket(ticketId);

        Assertions.assertThat(ticket).isNotNull()
                .extracting(Ticket::id)
                .isEqualTo(ticketId);

        return ticket;
    }
}
