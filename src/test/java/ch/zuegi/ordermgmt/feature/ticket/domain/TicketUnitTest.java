package ch.zuegi.ordermgmt.feature.ticket.domain;

import ch.zuegi.ordermgmt.feature.ticket.domain.command.CreateTicket;
import ch.zuegi.ordermgmt.feature.ticket.domain.command.CreateOrder;
import ch.zuegi.ordermgmt.feature.ticket.domain.vo.TicketId;
import ch.zuegi.ordermgmt.shared.aggregateRoot.AggregateRootValidationException;
import ch.zuegi.ordermgmt.shared.aggregateRoot.AggregateRootValidationMsg;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

class TicketUnitTest {


    @Test
    void create_order_ticket() {

        TicketId orderTicketId = new TicketId();
        List<CreateOrder> createCustomerOrderList = new ArrayList<>();
        CreateTicket createOrderTicket = CreateTicket.commandOf(orderTicketId, createCustomerOrderList);

        Ticket orderTicket = Ticket.create(orderTicketId, createOrderTicket);
    }

    @Test
    void create_order_ticket_with_invalid_orderticketid_throws_exception() {
        TicketId orderTicketId = null;
        List<CreateOrder> createCustomerOrderList = new ArrayList<>();
        CreateTicket createOrderTicket = CreateTicket.commandOf(orderTicketId, createCustomerOrderList);

        Assertions.assertThatExceptionOfType(AggregateRootValidationException.class)
                .isThrownBy(() -> Ticket.create(orderTicketId, createOrderTicket))
                .withMessage(AggregateRootValidationMsg.AGGREGATE_ID_MUST_NOT_BE_NULL);
    }

}
