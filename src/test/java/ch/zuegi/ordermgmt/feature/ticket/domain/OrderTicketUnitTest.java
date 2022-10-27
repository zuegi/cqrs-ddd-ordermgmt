package ch.zuegi.ordermgmt.feature.ticket.domain;

import ch.zuegi.ordermgmt.feature.ticket.domain.command.CreateOrderTicket;
import ch.zuegi.ordermgmt.feature.ticket.domain.vo.OrderTicketId;
import ch.zuegi.ordermgmt.shared.aggregateRoot.AggregateRootValidationException;
import ch.zuegi.ordermgmt.shared.aggregateRoot.AggregateRootValidationMsg;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;

class OrderTicketUnitTest {


    @Test
    void create_order_ticket() {

        OrderTicketId orderTicketId = new OrderTicketId();
        CreateOrderTicket createOrderTicket = CreateOrderTicket.commandOf(orderTicketId);

        OrderTicket orderTicket = OrderTicket.create(orderTicketId, createOrderTicket);
    }

    @Test
    void create_order_ticket_with_invalid_orderticketid_throws_exception() {
        OrderTicketId orderTicketId = null;
        Assertions.assertThatExceptionOfType(AggregateRootValidationException.class)
                .isThrownBy(() -> OrderTicket.create(orderTicketId, null))
                .withMessage(AggregateRootValidationMsg.AGGREGATE_ID_MUST_NOT_BE_NULL);
    }

}
