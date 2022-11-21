package ch.zuegi.ordermgmt.feature.ticket.domain;

import ch.zuegi.ordermgmt.feature.ticket.domain.vo.TicketNumber;
import ch.zuegi.ordermgmt.feature.ticket.domain.vo.TicketPositionNumber;
import ch.zuegi.ordermgmt.feature.ticket.domain.vo.TradeItemId;
import ch.zuegi.ordermgmt.shared.aggregateRoot.AggregateRoot;
import ch.zuegi.ordermgmt.shared.aggregateRoot.AggregateRootValidationException;
import ch.zuegi.ordermgmt.shared.aggregateRoot.AggregateRootValidationMsg;
import lombok.Getter;

import java.math.BigDecimal;

@Getter
public class TicketPosition extends AggregateRoot<TicketPosition, TicketPositionNumber> {


    public TicketPosition(TicketPositionNumber aggregateId) {
        super(aggregateId);
    }

    @Override
    public TicketPositionNumber id() {
        return this.aggregateId;
    }


    @Override
    protected AggregateRootBehavior<TicketPositionNumber> initialBehavior() {
        return null;
    }
}
