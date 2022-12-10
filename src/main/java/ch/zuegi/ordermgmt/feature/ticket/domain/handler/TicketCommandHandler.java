package ch.zuegi.ordermgmt.feature.ticket.domain.handler;

import ch.zuegi.ordermgmt.feature.ticket.domain.Ticket;
import ch.zuegi.ordermgmt.feature.ticket.domain.validator.TicketCommandValidator;
import ch.zuegi.ordermgmt.shared.Command;
import ch.zuegi.ordermgmt.shared.aggregateRoot.AggregateRootValidationException;
import ch.zuegi.ordermgmt.shared.aggregateRoot.AggregateRootValidationMsg;
import ch.zuegi.ordermgmt.shared.annotation.CommandHandler;
import ch.zuegi.ordermgmt.shared.annotation.CommandValidator;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.List;

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

        // filter all methods annotated with CommandValidator.class and with parameter equals command
        List<Method> methods = Arrays.stream(ticket.getClass().getDeclaredMethods())
                .filter(method -> method.isAnnotationPresent(CommandHandler.class))
                .filter(method -> Arrays.stream(method.getParameterTypes()).anyMatch(parameterType -> parameterType.isInstance(command)) || command == null)
                .toList();

        // it is not possible to call a method that throws a checked exception from a lambda directly.
        for (Method method : methods) {
            method.invoke(ticket, command);
        }
    }

    private void assertNotNull(Ticket ticket) {
        if (ticket == null) {
            throw new AggregateRootValidationException(AggregateRootValidationMsg.TICKET_MUST_NOT_BE_EMPTY);
        }
    }
}
