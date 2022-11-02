package ch.zuegi.ordermgmt.feature.vaughn.ticket.domain.shared;

import java.io.Serializable;

public abstract class Entity<ID> implements Serializable {
    protected final ID aggregateId;


    protected Entity(ID aggregateId) {
        this.aggregateId = aggregateId;
    }

    public abstract ID id();
}
