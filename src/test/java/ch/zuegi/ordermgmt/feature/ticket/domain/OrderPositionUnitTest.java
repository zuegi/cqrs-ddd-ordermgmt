package ch.zuegi.ordermgmt.feature.ticket.domain;

import ch.zuegi.ordermgmt.feature.ticket.domain.command.CreateOrderPosition;
import ch.zuegi.ordermgmt.feature.ticket.domain.vo.OrderPositionId;
import ch.zuegi.ordermgmt.feature.ticket.domain.vo.TradeItemId;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;

class OrderPositionUnitTest {


    @Test
    void create_order_position() {
        OrderPositionId orderPositionId = new OrderPositionId();
        BigDecimal menge = BigDecimal.TEN;
        TradeItemId tradeItemId = new TradeItemId() ;
        CreateOrderPosition createOrderPosition = CreateOrderPosition.commandOf(tradeItemId, menge);
        OrderPosition orderPosition = OrderPosition.create(orderPositionId);

        Assertions.assertThat(orderPosition).isInstanceOf(OrderPosition.class)
                .isNotNull();



    }

}
