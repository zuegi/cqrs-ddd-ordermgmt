package ch.zuegi.ordermgmt.feature.vaughn.ticket.domain.shared;

import ch.zuegi.ordermgmt.feature.test.shared.AggregateId;
import ch.zuegi.ordermgmt.feature.test.shared.DomainCommandHandler;
import ch.zuegi.ordermgmt.feature.vaughn.ticket.domain.shared.Entity;
import ch.zuegi.ordermgmt.shared.Command;

import javax.validation.constraints.NotNull;
import java.util.Map;

public abstract class AggregateRoot<E, ID> extends Entity {

    protected AggregateRoot(ID aggregateId) {
        super(aggregateId);
    }

}
