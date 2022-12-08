package ch.zuegi.ordermgmt.feature.ticket.application.ticket;


import ch.zuegi.ordermgmt.feature.ticket.domain.Ticket;
import ch.zuegi.ordermgmt.feature.ticket.domain.TicketRepository;
import ch.zuegi.ordermgmt.feature.ticket.domain.command.CreateTicketCommand;
import ch.zuegi.ordermgmt.feature.ticket.domain.handler.TicketCommandHandler;
import ch.zuegi.ordermgmt.feature.ticket.domain.vo.TicketId;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.lang.reflect.InvocationTargetException;

@Service
@AllArgsConstructor
public class TicketService {

    TicketCommandHandler commandHandler;
    TicketRepository repository;


    public Ticket createTicket(TicketId ticketId, CreateTicketCommand createTicketCommand) {
       // TODO find ticket in repository or create Ticket
        Ticket ticket = repository.findByTicketId()
                .orElse(new Ticket(ticketId));

        // handle CreateTicketCommand
        try {
            commandHandler.handle(ticket, createTicketCommand);
        } catch (InvocationTargetException | IllegalAccessException e) {
            throw new RuntimeException(e);
        }

        return ticket;
    }
}
