package ch.zuegi.ordermgmt.feature.ticket.domain;

import ch.zuegi.ordermgmt.feature.ticket.domain.vo.TicketNumber;
import org.assertj.core.api.Assertions;

public class TicketTestHelper {
    public static final String TICKET_ID = "12345";

    public static Ticket ticketForTest() {
        TicketNumber ticketNumber = new TicketNumber(TICKET_ID);
        Ticket ticket = new Ticket(ticketNumber);

        Assertions.assertThat(ticket).isNotNull()
                .extracting(Ticket::id)
                .isEqualTo(ticketNumber);

        return ticket;
    }
}
