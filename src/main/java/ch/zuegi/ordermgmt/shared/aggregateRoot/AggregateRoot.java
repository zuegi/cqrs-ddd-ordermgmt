package ch.zuegi.ordermgmt.shared.aggregateRoot;

import ch.zuegi.ordermgmt.shared.Command;
import ch.zuegi.ordermgmt.shared.CommandValidator;
import ch.zuegi.ordermgmt.shared.Entity;
import ch.zuegi.ordermgmt.shared.RandomUUID;
import lombok.Builder;
import lombok.Getter;

import java.io.Serializable;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

@Getter
public abstract class AggregateRoot<E, ID extends Serializable> extends Entity<ID> {

    private AggregateRootValidators validators;

    public AggregateRoot(ID aggregateID) {
        super(aggregateID);
        this.validators = initialValidators();
    }

    protected abstract AggregateRootValidators initialValidators();


    public <A extends AggregateRoot<A, ? extends RandomUUID>,  C extends Command> void validate(A aggregate, C command) {
        if (command == null) {
            throw new AggregateRootValidationException(AggregateRootValidationMsg.AGGREGATE_COMMAND_MUST_NOT_BE_NULL);
        }
        CommandValidator<A, C> commandValidator = validators.getByCommand(command);
        commandValidator.validate(aggregate, command);
    }

    /*
        AggreateRootBehaviour
     */

    @Builder
    public static class AggregateRootValidators {

        protected final Map<Class<? extends Command>, CommandValidator<?extends AggregateRoot, ? extends Command>> validators;

        public AggregateRootValidators(Map<Class<? extends Command>, CommandValidator<? extends AggregateRoot,? extends Command>> validators) {
            this.validators = Collections.unmodifiableMap(validators);
        }

        @SuppressWarnings("unchecked")
        public <A extends AggregateRoot<A, ? extends RandomUUID>, C extends Command>  CommandValidator<A, C> getByCommand(C command) {
            return  (CommandValidator<A, C>) validators.get(command.getClass());
        }
    }

}
