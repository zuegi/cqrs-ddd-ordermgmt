package ch.zuegi.ordermgmt.feature.test.shared;

import ch.zuegi.ordermgmt.shared.Command;
import ch.zuegi.ordermgmt.shared.Entity;

import javax.validation.constraints.NotNull;
import java.util.Map;

public abstract class AggregateRoot<E, ID> extends AggregateId<ID> {


    protected Map<Class<? extends Command>,DomainCommandHandler<?, ? extends Command>> behaviourMap;

    protected AggregateRoot(ID aggregateId) {
        super(aggregateId);
        initBehaviour();
    }

    public abstract void initBehaviour();



    public Map<Class<? extends Command>,DomainCommandHandler<?, ? extends Command>> getBehaviourMap() {
        return behaviourMap;
    }

    @SuppressWarnings("unchecked")
    public <C extends Command> void handle(@NotNull C command) {
        DomainCommandHandler<E, C> commandHandler = (DomainCommandHandler<E, C>) this.getCommandHandler(command.getClass());
        commandHandler.handle(command);
    }


    private DomainCommandHandler<?, ? extends Command> getCommandHandler(Class<? extends Command> command) {
       return getBehaviourMap().get(command);
    }
}
