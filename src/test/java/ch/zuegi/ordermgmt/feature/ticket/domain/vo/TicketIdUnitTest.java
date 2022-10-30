package ch.zuegi.ordermgmt.feature.ticket.domain.vo;

import ch.zuegi.ordermgmt.shared.EventPrefix;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;


class TicketIdUnitTest {

    @Test
    void erstelle_order_ticket_id() {
        TicketId orderTicketId = new TicketId();
        Assertions.assertThat(orderTicketId)
                .isNotNull()
                .extracting(TicketId::getPrefix)
                .isEqualTo(EventPrefix.OTP.name()+"-%s");
    }

    @Test
    void erstelle_zwei_identische_order_ticket_id() {
        TicketId orderTicketId = new TicketId();
        TicketId anotherOrderTicketId = new TicketId(orderTicketId.id);

        Assertions.assertThat(orderTicketId.sameValueAs(anotherOrderTicketId)).isTrue();
    }

    @Test
    void erstelle_zwei_nicht_identische_order_ticket_id() {
        TicketId orderTicketId = new TicketId();
        TicketId anotherOrderTicketId = new TicketId();
        Assertions.assertThat(orderTicketId).isNotEqualTo(anotherOrderTicketId);
        Assertions.assertThat(orderTicketId.sameValueAs(anotherOrderTicketId)).isFalse();

    }
}
