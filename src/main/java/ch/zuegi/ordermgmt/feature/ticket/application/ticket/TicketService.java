package ch.zuegi.ordermgmt.feature.ticket.application.ticket;


import ch.zuegi.ordermgmt.feature.ticket.domain.command.AddTicketPositionCommand;
import ch.zuegi.ordermgmt.feature.ticket.domain.command.ConfirmTicketCommand;
import ch.zuegi.ordermgmt.feature.ticket.domain.command.CreateTicketCommand;
import ch.zuegi.ordermgmt.feature.ticket.domain.command.RemoveTicketPositionCommand;
import ch.zuegi.ordermgmt.feature.ticket.domain.handler.TicketCommandHandler;
import ch.zuegi.ordermgmt.shared.Command;
import ch.zuegi.ordermgmt.shared.aggregateRoot.AggregateRootValidationException;
import ch.zuegi.ordermgmt.shared.aggregateRoot.AggregateRootValidationMsg;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.lang.reflect.InvocationTargetException;

@Slf4j
@Service
@AllArgsConstructor
public class TicketService {

    private TicketCommandHandler commandHandler;


    public void createTicket(CreateTicketCommand createTicketCommand) {

        assertNotNull(createTicketCommand);

        try {
            commandHandler.handle(createTicketCommand.getTicketId(), createTicketCommand);
        } catch (InvocationTargetException | IllegalAccessException e) {
            // TODO korrekte Exception schmeissen
            log.info("Invalid Command: {}", createTicketCommand);
        }
    }

    public void confirmTicket(ConfirmTicketCommand confirmTicketCommand) {

    }


    public void addTicketPosition(AddTicketPositionCommand addTicketPositionCommand) {
        try {
            commandHandler.handle(addTicketPositionCommand.getTicketId(), addTicketPositionCommand);
        } catch (InvocationTargetException | IllegalAccessException e) {
            // TODO korrekte Exception schmeissen
            log.info("Invalid Command: {}", addTicketPositionCommand);
        }
    }

    public void removeTicketPosition(RemoveTicketPositionCommand removeTicketPositionCommand) {

    }

    private void assertNotNull(Command command) {
        if (command == null) {
            throw new AggregateRootValidationException(AggregateRootValidationMsg.TICKET_COMMAND_MUST_NOT_BE_EMPTY);
        }
    }
}
