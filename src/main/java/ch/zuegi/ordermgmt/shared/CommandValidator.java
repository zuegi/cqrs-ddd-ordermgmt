package ch.zuegi.ordermgmt.shared;

import ch.zuegi.ordermgmt.shared.aggregateRoot.AggregateRoot;

public interface CommandValidator<A extends AggregateRoot<A, ? extends RandomUUID>, C extends Command> {

    void validate(A aggregate, C command);
}
