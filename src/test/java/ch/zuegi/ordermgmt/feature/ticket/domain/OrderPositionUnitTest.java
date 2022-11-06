package ch.zuegi.ordermgmt.feature.ticket.domain;

import ch.zuegi.ordermgmt.feature.ticket.domain.command.CreateOrderPosition;
import ch.zuegi.ordermgmt.feature.ticket.domain.entity.OrderPositionEntity;
import ch.zuegi.ordermgmt.feature.ticket.domain.vo.OrderPositionId;
import ch.zuegi.ordermgmt.feature.ticket.domain.vo.TradeItemId;
import ch.zuegi.ordermgmt.shared.aggregateRoot.AggregateRootValidationException;
import ch.zuegi.ordermgmt.shared.aggregateRoot.AggregateRootValidationMsg;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;

class OrderPositionUnitTest {


    @Test
    void create_order_position() {
        // given
        OrderPositionId orderPositionId = new OrderPositionId();
        CreateOrderPosition createOrderPosition = CreateOrderPosition.commandOf( new TradeItemId(), BigDecimal.TEN);

        // when
        OrderPosition orderPosition = OrderPosition.create(orderPositionId);

        // then
        Assertions.assertThat(orderPosition).isInstanceOf(OrderPosition.class)
                .isNotNull();

        // when
        OrderPositionEntity entity = (OrderPositionEntity) orderPosition.handleCommand(createOrderPosition);

        // then
        Assertions.assertThat(entity).isInstanceOf(OrderPositionEntity.class)
                .isNotNull()
                .extracting(OrderPositionEntity::id, OrderPositionEntity::getTradeItemId, OrderPositionEntity::getMenge)
                .contains(orderPositionId, createOrderPosition.getTradeItemId(), createOrderPosition.getMenge());

    }

    @Test
    void create_order_position_with_order_position_id_is_null() {
        OrderPositionId orderPositionId = null;
        Assertions.assertThatExceptionOfType(AggregateRootValidationException.class)
                .isThrownBy(() -> OrderPosition.create(orderPositionId))
                .withMessage(AggregateRootValidationMsg.AGGREGATE_ID_MUST_NOT_BE_NULL);
    }

}
