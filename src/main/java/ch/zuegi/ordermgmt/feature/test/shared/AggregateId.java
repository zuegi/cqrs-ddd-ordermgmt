package ch.zuegi.ordermgmt.feature.test.shared;

public abstract class AggregateId<ID> {
    protected final ID aggregateId;

    protected AggregateId(ID aggregateId) {
        this.aggregateId = aggregateId;
    }

    public abstract ID id();
}
