package ch.zuegi.ordermgmt.feature.ticket.domain.validator;

import ch.zuegi.ordermgmt.feature.ticket.domain.Ticket;
import ch.zuegi.ordermgmt.feature.ticket.domain.command.CreateTicketCommand;
import ch.zuegi.ordermgmt.feature.ticket.domain.entity.TicketLifeCycleState;
import ch.zuegi.ordermgmt.shared.CommandValidator;
import ch.zuegi.ordermgmt.shared.aggregateRoot.AggregateRootValidationException;
import ch.zuegi.ordermgmt.shared.aggregateRoot.AggregateRootValidationMsg;

public class CreateTicketCommandValidator implements CommandValidator<Ticket, CreateTicketCommand> {

    @Override
    public void validate(Ticket aggregate, CreateTicketCommand command) {
        if (command.getCreateTicketPositionCommands().isEmpty()) {
            throw new AggregateRootValidationException(AggregateRootValidationMsg.TICKET_COMMAND_TICKET_POSITION_SET_MUST_NOT_BE_EMPTY);
        }
    }
}
