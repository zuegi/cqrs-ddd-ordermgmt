package ch.zuegi.ordermgmt.feature.ticket.domain.handler;

import ch.zuegi.ordermgmt.feature.ticket.domain.Ticket;
import ch.zuegi.ordermgmt.feature.ticket.domain.validator.TicketCommandValidator;
import ch.zuegi.ordermgmt.shared.Command;
import ch.zuegi.ordermgmt.shared.aggregateRoot.AggregateRootValidationException;
import ch.zuegi.ordermgmt.shared.aggregateRoot.AggregateRootValidationMsg;
import ch.zuegi.ordermgmt.shared.annotation.CommandHandler;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Arrays;

@Slf4j
@AllArgsConstructor
@Component
public class TicketCommandHandler {

    private TicketCommandValidator ticketCommandValidator;

    public void handle(Ticket ticket, Command command) throws InvocationTargetException, IllegalAccessException {

        assertNotNull(ticket);

        log.debug("Ticket: {}", ticket);

        // validate first
        ticketCommandValidator.validate(ticket, command);

        // filter all methods annotated with CommandHandler.class and with parameter equals command
        Method method = findMethodForCommand(ticket, command);

        method.invoke(ticket, command);
    }

    private Method findMethodForCommand(Ticket ticket, Command command) {
         return Arrays.stream(ticket.getClass().getDeclaredMethods())
                .filter(m -> m.isAnnotationPresent(CommandHandler.class))
                 // filter for params in method signature
                .filter(m -> Arrays.stream(m.getParameterTypes()).anyMatch(parameterType -> parameterType.isInstance(command)) || command == null)
                .findFirst()
                .orElseThrow(() -> new AggregateRootValidationException(AggregateRootValidationMsg.TICKET_HANDLE_COMMAND_INVALID));

    }

    private void assertNotNull(Ticket ticket) {
        if (ticket == null) {
            throw new AggregateRootValidationException(AggregateRootValidationMsg.TICKET_MUST_NOT_BE_EMPTY);
        }
    }
}
