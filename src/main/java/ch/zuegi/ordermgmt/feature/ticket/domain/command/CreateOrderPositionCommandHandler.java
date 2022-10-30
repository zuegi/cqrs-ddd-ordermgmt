package ch.zuegi.ordermgmt.feature.ticket.domain.command;

import ch.zuegi.ordermgmt.feature.ticket.domain.entity.OrderPositionEntity;
import ch.zuegi.ordermgmt.feature.ticket.domain.vo.OrderPositionId;
import ch.zuegi.ordermgmt.shared.Command;
import ch.zuegi.ordermgmt.shared.CommandHandler;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class CreateOrderPositionCommandHandler implements CommandHandler<CreateOrderPosition, OrderPositionEntity, OrderPositionId> {


    @Override
    public OrderPositionEntity handle(OrderPositionId  orderPositionId, CreateOrderPosition createOrderPosition) {

        log.debug("{} called with {}", this.getClass().getName(), createOrderPosition);

        OrderPositionEntity orderPositionEntity = new OrderPositionEntity(orderPositionId);
        orderPositionEntity.setTradeItemId(createOrderPosition.getTradeItemId());
        orderPositionEntity.setMenge(createOrderPosition.getMenge());

        return orderPositionEntity;
    }

}
