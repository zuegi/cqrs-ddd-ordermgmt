package ch.zuegi.ordermgmt.shared.aggregateRoot;

import ch.zuegi.ordermgmt.shared.Command;
import ch.zuegi.ordermgmt.shared.CommandHandler;
import ch.zuegi.ordermgmt.shared.Entity;
import lombok.Getter;

import java.io.Serializable;

@Getter
public abstract class AggregateRoot<E, ID extends Serializable> extends Entity<ID> {

    protected final ID aggregateId;
    private final AggregateRootBehavior<ID> behavior;
    public AggregateRoot(ID aggregateID) {
        super(aggregateID);
        this.aggregateId = aggregateID;
        this.validate();
        this.behavior = initialBehavior();
    }


    protected abstract AggregateRootBehavior<ID> initialBehavior();

    protected void validate() {
        if (aggregateId == null) {
            throw new AggregateRootValidationException(AggregateRootValidationMsg.AGGREGATE_ID_MUST_NOT_BE_NULL);
        }
    }

    public Entity handleCommand(Command command) {
        CommandHandler<? extends Command, ? extends Entity<ID>, ID> commandHandler = behavior.commandHandlers.get(command.getClass());
        return commandHandler.handle( aggregateId, command);
    }
}
