package ch.zuegi.ordermgmt.feature.ticket.domain.validator;

import ch.zuegi.ordermgmt.feature.ticket.domain.command.CreateTicketPositionCommand;
import ch.zuegi.ordermgmt.shared.CommandValidator;
import ch.zuegi.ordermgmt.shared.aggregateRoot.AggregateRootValidationException;
import ch.zuegi.ordermgmt.shared.aggregateRoot.AggregateRootValidationMsg;

public class CreateTicketCommandPositionValidator implements CommandValidator<CreateTicketPositionCommand> {
    @Override
    public void validate(CreateTicketPositionCommand command) {
        System.out.println(command.toString());
    }
}
