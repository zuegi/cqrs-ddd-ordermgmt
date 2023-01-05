package ch.zuegi.ordermgmt.shared;

import ch.zuegi.ordermgmt.shared.aggregateRoot.AggregateRootValidationException;
import ch.zuegi.ordermgmt.shared.aggregateRoot.AggregateRootValidationMsg;
import lombok.experimental.SuperBuilder;

import java.io.Serializable;

@SuperBuilder
public abstract class Entity<ID> implements Serializable {

    protected final ID aggregateId;

    protected Entity(ID aggregateId) {
        if (aggregateId == null) {
            throw new AggregateRootValidationException(AggregateRootValidationMsg.AGGREGATE_ID_MUST_NOT_BE_NULL);
        }
        this.aggregateId = aggregateId;
    }


    public abstract ID id();
}
