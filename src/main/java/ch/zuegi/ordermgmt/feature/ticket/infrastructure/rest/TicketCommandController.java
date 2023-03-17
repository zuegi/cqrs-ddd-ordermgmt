package ch.zuegi.ordermgmt.feature.ticket.infrastructure.rest;

import ch.zuegi.ordermgmt.feature.ticket.domain.command.AddTicketPositionCommand;
import ch.zuegi.ordermgmt.feature.ticket.domain.command.ConfirmTicketCommand;
import ch.zuegi.ordermgmt.feature.ticket.domain.command.CreateTicketCommand;
import ch.zuegi.ordermgmt.shared.gateway.command.CommandGateway;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@Slf4j
@RestController
@RequestMapping(value = "/api/ticket", produces = MediaType.APPLICATION_JSON_VALUE)
public class TicketCommandController {

    private final CommandGateway commandGateway;

    public TicketCommandController(CommandGateway commandGateway) {
        this.commandGateway = commandGateway;
    }

    @PostMapping("/create")
    public ResponseEntity<String> createTicket(@RequestBody @Valid CreateTicketCommand createTicketCommand) {
        return ResponseEntity.ok(commandGateway.send(createTicketCommand));
    }

    @PostMapping(path="/addTicketPosition")
    public ResponseEntity<String> addTicketPosition(@RequestBody @Valid AddTicketPositionCommand addTicketPositionCommand) {
        return ResponseEntity.ok(commandGateway.send(addTicketPositionCommand));
    }

    @PostMapping(path = "/confirm")
    public ResponseEntity<String> confirmTicket(@RequestBody @Valid ConfirmTicketCommand command) {
        return ResponseEntity.ok(commandGateway.send(command));
    }


}
