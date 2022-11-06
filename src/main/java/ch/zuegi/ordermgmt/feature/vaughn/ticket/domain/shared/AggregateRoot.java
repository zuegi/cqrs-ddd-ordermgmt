package ch.zuegi.ordermgmt.feature.vaughn.ticket.domain.shared;

public abstract class AggregateRoot<E, ID> extends Entity {

    protected AggregateRoot(ID aggregateId) {
        super(aggregateId);
    }

}
