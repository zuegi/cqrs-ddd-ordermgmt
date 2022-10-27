package ch.zuegi.ordermgmt.shared.aggregateRoot;

import ch.zuegi.ordermgmt.shared.Command;
import ch.zuegi.ordermgmt.shared.CommandHandler;
import ch.zuegi.ordermgmt.shared.Entity;
import lombok.Getter;

import java.io.Serializable;

@Getter
public abstract class AggregateRoot<E, RandomUUID extends Serializable> implements Entity<E, RandomUUID> {

    protected final RandomUUID aggregateId;
    private final AggregateRootBehavior<RandomUUID> behavior;
    public AggregateRoot(RandomUUID aggregateID) {
       this.aggregateId = aggregateID;
        this.behavior = initialBehavior();
    }

    protected abstract void validate();

    protected abstract AggregateRootBehavior<RandomUUID> initialBehavior();



    public Entity<E, RandomUUID> handleCommand(Command command) {
        CommandHandler<? extends Command, ? extends Entity> commandHandler = behavior.commandHandlers.get(command.getClass());
        return commandHandler.handle(command);
    }
}
