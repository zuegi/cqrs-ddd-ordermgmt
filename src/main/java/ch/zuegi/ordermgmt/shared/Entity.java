package ch.zuegi.ordermgmt.shared;

import java.io.Serializable;

public abstract class Entity<ID> implements Serializable {
    protected final ID aggregateId;


    protected Entity(ID aggregateId) {
        this.aggregateId = aggregateId;
    }

    public abstract ID id();
}
