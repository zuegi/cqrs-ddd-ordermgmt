package ch.zuegi.ordermgmt.feature.ticket.domain;

import ch.zuegi.ordermgmt.feature.ticket.domain.command.CreateOrder;
import ch.zuegi.ordermgmt.feature.ticket.domain.command.CreateOrderPosition;
import ch.zuegi.ordermgmt.feature.ticket.domain.entity.TradeDealType;
import ch.zuegi.ordermgmt.feature.ticket.domain.vo.OrderId;
import ch.zuegi.ordermgmt.feature.ticket.domain.vo.OrderPositionId;
import ch.zuegi.ordermgmt.feature.ticket.domain.vo.TradeItemId;
import ch.zuegi.ordermgmt.shared.aggregateRoot.AggregateRootValidationException;
import ch.zuegi.ordermgmt.shared.aggregateRoot.AggregateRootValidationMsg;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.util.List;

class OrderUnitTest {


    @Test
    void create_order_with_order_position() {

    }

    @Test
    void create_order_valid() {
        // given
        OrderId orderId = new OrderId();
        TradeItemId tradeItemId = new TradeItemId();
        TradeItemId tradeItemId1 = new TradeItemId();
        CreateOrderPosition createOrderPosition = CreateOrderPosition.commandOf(tradeItemId, BigDecimal.ONE );
        CreateOrderPosition createOrderPosition1 = CreateOrderPosition.commandOf(tradeItemId1, BigDecimal.TEN );
        List<CreateOrderPosition> createOrderPositionList = List.of(createOrderPosition, createOrderPosition1);
        CreateOrder createOrder = CreateOrder.commandOf(TradeDealType.BUY, createOrderPositionList);

        // when
        Order order = Order.create(orderId);
        // then
        Assertions.assertThat(order).isInstanceOf(Order.class).isNotNull();

        order.handleCommand(createOrder);

        // und wie verbinde ich jetzt alles zusammen von Order und OrderPosition?
    }


    @Test
    void create_order_with_order_id_is_null() {
        OrderId orderId = null;
        Assertions.assertThatExceptionOfType(AggregateRootValidationException.class)
                .isThrownBy(() -> Order.create(orderId))
                .withMessage(AggregateRootValidationMsg.AGGREGATE_ID_MUST_NOT_BE_NULL);
    }

}
