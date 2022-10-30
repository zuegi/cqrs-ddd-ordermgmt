package ch.zuegi.ordermgmt.feature.ticket.domain.entity;

import ch.zuegi.ordermgmt.feature.ticket.domain.vo.OrderPositionId;
import ch.zuegi.ordermgmt.feature.ticket.domain.vo.TradeItemId;
import ch.zuegi.ordermgmt.shared.Entity;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

@Getter
@Setter
public class OrderPositionEntity extends Entity<OrderPositionId> {

    TradeItemId tradeItemId;
    BigDecimal menge;

    public OrderPositionEntity(OrderPositionId aggregateId) {
        super(aggregateId);
    }


    @Override
    public OrderPositionId id() {
        return this.aggregateId;
    }
}
