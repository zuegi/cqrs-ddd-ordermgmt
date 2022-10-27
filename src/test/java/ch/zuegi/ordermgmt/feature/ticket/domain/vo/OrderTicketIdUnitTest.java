package ch.zuegi.ordermgmt.feature.ticket.domain.vo;

import ch.zuegi.ordermgmt.shared.EventPrefix;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;


class OrderTicketIdUnitTest {

    @Test
    void erstelle_order_ticket_id() {
        OrderTicketId orderTicketId = new OrderTicketId();
        Assertions.assertThat(orderTicketId)
                .isNotNull()
                .extracting(OrderTicketId::getPrefix)
                .isEqualTo(EventPrefix.ORT.name()+"-%s");
    }

    @Test
    void erstelle_zwei_identische_order_ticket_id() {
        OrderTicketId orderTicketId = new OrderTicketId();
        OrderTicketId anotherOrderTicketId = new OrderTicketId(orderTicketId.id);

        Assertions.assertThat(orderTicketId.sameValueAs(anotherOrderTicketId)).isTrue();
    }

    @Test
    void erstelle_zwei_nicht_identische_order_ticket_id() {
        OrderTicketId orderTicketId = new OrderTicketId();
        OrderTicketId anotherOrderTicketId = new OrderTicketId();
        Assertions.assertThat(orderTicketId).isNotEqualTo(anotherOrderTicketId);
        Assertions.assertThat(orderTicketId.sameValueAs(anotherOrderTicketId)).isFalse();

    }
}
