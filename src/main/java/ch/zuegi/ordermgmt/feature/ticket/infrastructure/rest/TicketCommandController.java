package ch.zuegi.ordermgmt.feature.ticket.infrastructure.rest;

import ch.zuegi.ordermgmt.feature.ticket.application.ticket.TicketService;
import ch.zuegi.ordermgmt.feature.ticket.domain.command.AddTicketPositionCommand;
import ch.zuegi.ordermgmt.feature.ticket.domain.command.ConfirmTicketCommand;
import ch.zuegi.ordermgmt.feature.ticket.domain.command.CreateTicketCommand;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@Slf4j
@AllArgsConstructor
@RestController
@RequestMapping(value = "/api/ticket", produces = MediaType.APPLICATION_JSON_VALUE)
public class TicketCommandController {

    private TicketService ticketService;

    @PostMapping
    public void createTicket(@RequestBody @Valid CreateTicketCommand createTicketCommand) {
        ticketService.createTicket(createTicketCommand);
    }

    @PostMapping(path="/addTicketPosition")
    public void addTicketPosition(@RequestBody @Valid AddTicketPositionCommand addTicketPositionCommand) {
        ticketService.addTicketPosition(addTicketPositionCommand);
    }

    @PostMapping(path = "/confirm")
    public void confirmTicket(@RequestBody @Valid ConfirmTicketCommand command) {
        ticketService.confirmTicket(command);
    }


}
