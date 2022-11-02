package ch.zuegi.ordermgmt.feature.vaughn.ticket.domain;


import ch.zuegi.ordermgmt.feature.vaughn.ticket.domain.vo.TicketId;
import ch.zuegi.ordermgmt.feature.vaughn.ticket.domain.vo.TicketPositionId;
import ch.zuegi.ordermgmt.feature.vaughn.ticket.domain.vo.TradeItemId;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.*;

class TicketTest {


    @Test
    void createTicket() {
        TicketId ticketId = new TicketId("12345");
        Ticket ticket = new Ticket(ticketId);

        Assertions.assertThat(ticket).isNotNull()
                .extracting(Ticket::id)
                .isEqualTo(ticketId);
    }

    @Test
    void addTicketPositionToTicket() {
        Ticket ticket = ticketForTest();
        TicketPosition ticketPosition = new TicketPosition(new TicketPositionId(), ticket.id(), new TradeItemId(), BigDecimal.TEN);
        ticket.plannedTicketPosition(ticketPosition);


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
