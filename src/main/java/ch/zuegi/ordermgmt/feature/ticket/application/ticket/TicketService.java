package ch.zuegi.ordermgmt.feature.ticket.application.ticket;


import ch.zuegi.ordermgmt.feature.ticket.domain.command.AddTicketPositionCommand;
import ch.zuegi.ordermgmt.feature.ticket.domain.command.CreateTicketCommand;
import ch.zuegi.ordermgmt.feature.ticket.domain.handler.TicketCommandHandler;
import ch.zuegi.ordermgmt.feature.ticket.domain.vo.TicketId;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.lang.reflect.InvocationTargetException;

@Slf4j
@Service
@AllArgsConstructor
public class TicketService {

    private TicketCommandHandler commandHandler;


    public void createTicket(TicketId ticketId, CreateTicketCommand createTicketCommand) {
        try {
            commandHandler.handle(ticketId, createTicketCommand);
        } catch (InvocationTargetException | IllegalAccessException e) {
            // TODO korrekte Exception schmeissen
            log.info("Invalid Command: {}", createTicketCommand.toString());
        }
    }


    public void addTicketPosition(TicketId ticketId, AddTicketPositionCommand addTicketPositionCommand) {
        try {
            commandHandler.handle(ticketId, addTicketPositionCommand);
        } catch (InvocationTargetException | IllegalAccessException e) {
            // TODO korrekte Exception schmeissen
            log.info("Invalid Command: {}", addTicketPositionCommand.toString());
        }
    }
}
