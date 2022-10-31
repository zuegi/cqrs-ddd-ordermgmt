package ch.zuegi.ordermgmt.feature.test;

import ch.zuegi.ordermgmt.feature.test.shared.AggregateId;
import ch.zuegi.ordermgmt.shared.Entity;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class AuthorEntity extends AggregateId<AuthorId> {

    String vorname;
    String nachname;

    protected AuthorEntity(AuthorId aggregateId) {
        super(aggregateId);
    }

    @Override
    public AuthorId id() {
        return this.aggregateId;
    }
}
