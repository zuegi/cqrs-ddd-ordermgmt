package ch.zuegi.ordermgmt.shared.aggregateRoot;

import ch.zuegi.ordermgmt.shared.Command;
import ch.zuegi.ordermgmt.shared.CommandHandler;
import ch.zuegi.ordermgmt.shared.Entity;
import ch.zuegi.ordermgmt.shared.Event;

import java.util.HashMap;
import java.util.Map;

public class AggregateRootBehaviorBuilder<RandomUUID> {

    private final Map<Class<? extends Command>, CommandHandler<? extends Command, ? extends Entity>> commandHandlers = new HashMap<>();

    public AggregateRootBehavior<RandomUUID> build() {
        return new AggregateRootBehavior<>(commandHandlers);
    }


    public <A extends Command, B extends Event> void setCommandHandler(Class<? extends Command> commandClass, CommandHandler<? extends Command, ? extends Entity> handler) {
        commandHandlers.put( commandClass,  handler);
    }

}
