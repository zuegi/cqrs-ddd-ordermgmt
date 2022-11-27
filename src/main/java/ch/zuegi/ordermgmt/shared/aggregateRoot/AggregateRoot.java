package ch.zuegi.ordermgmt.shared.aggregateRoot;

import ch.zuegi.ordermgmt.shared.Entity;
import lombok.Getter;

import java.io.Serializable;

@Getter
public abstract class AggregateRoot<E, ID extends Serializable> extends Entity<ID> {



    public AggregateRoot(ID aggregateID) {
        super(aggregateID);
        validate();
    }
    protected abstract void validate();

}
