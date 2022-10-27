package ch.zuegi.ordermgmt.shared.aggregateRoot;

import ch.zuegi.ordermgmt.shared.Command;
import ch.zuegi.ordermgmt.shared.CommandHandler;
import ch.zuegi.ordermgmt.shared.Entity;

import java.util.Map;

public class AggregateRootBehavior<RandomUUID> {

    protected final Map<Class<? extends Command>, CommandHandler<? extends Command, ? extends Entity>> commandHandlers;

    public AggregateRootBehavior(Map<Class<? extends Command>, CommandHandler<? extends Command, ? extends Entity>> commandHandlers) {
        this.commandHandlers = commandHandlers;
    }

}
