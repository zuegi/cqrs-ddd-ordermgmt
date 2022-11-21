package ch.zuegi.ordermgmt.shared.aggregateRoot;

import ch.zuegi.ordermgmt.feature.ticket.domain.command.SaveTicketCommand;
import ch.zuegi.ordermgmt.feature.ticket.domain.vo.TicketNumber;
import ch.zuegi.ordermgmt.shared.*;
import io.vavr.control.Either;
import lombok.Getter;

import java.io.Serializable;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

@Getter
public abstract class AggregateRoot<E, ID extends Serializable> extends Entity<ID> {


    private AggregateRootBehavior<ID> behavior;

    public AggregateRoot(ID aggregateID) {
        super(aggregateID);
        this.behavior = initialBehavior();
    }

    protected abstract AggregateRootBehavior<ID> initialBehavior();


    public <A extends Command, B extends DomainEvent> B handle(A command) {
        CommandHandler<A, B, ID> commandHandler = (CommandHandler<A, B, ID>) behavior.handlers.get(command.getClass());
        return commandHandler.handle(aggregateId, command);
    }

    /*
        AggreateRootBehaviour
     */

    public static class AggregateRootBehavior<ID> {

        protected final Map<Class<? extends Command>, CommandHandler<? extends Command, ? extends DomainEvent, ID>> handlers;

        public AggregateRootBehavior(Map<Class<? extends Command>, CommandHandler<? extends Command, ? extends DomainEvent, ID>> handlers) {
            this.handlers = Collections.unmodifiableMap(handlers);
        }
    }

    public static class AggregateRootBehaviorBuilder<ID> {

        private final Map<Class<? extends Command>, CommandHandler<? extends Command, ? extends DomainEvent, ID>> handlers = new HashMap<>();

        public <A extends Command, B extends DomainEvent> void setCommandHandler(Class<A> commandClass, CommandHandler<A, B, ID> handler) {
            handlers.put(commandClass, handler);
        }

        public AggregateRootBehavior<ID> build() {
            return new AggregateRootBehavior<>(handlers);
        }
    }

}
