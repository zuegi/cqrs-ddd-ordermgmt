package ch.zuegi.ordermgmt.shared.aggregateRoot;

import ch.zuegi.ordermgmt.shared.Command;
import ch.zuegi.ordermgmt.shared.CommandHandler;
import ch.zuegi.ordermgmt.shared.Entity;
import lombok.Getter;

import javax.validation.constraints.NotNull;
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

    @SuppressWarnings("unchecked")
    public <A extends Command> Entity<ID> handleCommand(@NotNull  A command) {

        // FIXME wie kann ich diesen unchecked cast verhindern, ohne die Annotation auf der Methode?
        CommandHandler<A, ? extends Entity<ID>, ID> commandHandler = (CommandHandler<A, ? extends Entity<ID>, ID>) behavior.commandHandlers.get(command.getClass());
        return commandHandler.handle(aggregateId, command);
    }

}
