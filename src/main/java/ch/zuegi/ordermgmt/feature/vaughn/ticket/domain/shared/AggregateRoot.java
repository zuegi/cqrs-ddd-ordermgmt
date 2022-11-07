package ch.zuegi.ordermgmt.feature.vaughn.ticket.domain.shared;

import ch.zuegi.ordermgmt.shared.Command;
import ch.zuegi.ordermgmt.shared.CommandHandler;
import ch.zuegi.ordermgmt.shared.Entity;
import ch.zuegi.ordermgmt.shared.aggregateRoot.AggregateRootBehavior;
import ch.zuegi.ordermgmt.shared.aggregateRoot.AggregateRootValidationException;
import ch.zuegi.ordermgmt.shared.aggregateRoot.AggregateRootValidationMsg;
import lombok.Getter;

import javax.validation.constraints.NotNull;
import java.io.Serializable;

@Getter
public abstract class AggregateRoot<E, ID extends Serializable> extends Entity<ID> {



    public AggregateRoot(ID aggregateID) {
        super(aggregateID);
    }
    protected abstract void validate();

}
