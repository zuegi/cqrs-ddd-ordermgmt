package ch.zuegi.ordermgmt.feature.ticket.domain.validator;

import ch.zuegi.ordermgmt.feature.ticket.domain.Ticket;
import ch.zuegi.ordermgmt.shared.Command;
import ch.zuegi.ordermgmt.shared.aggregateRoot.AggregateRootValidationException;
import ch.zuegi.ordermgmt.shared.aggregateRoot.AggregateRootValidationMsg;
import ch.zuegi.ordermgmt.shared.annotation.CommandValidator;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Arrays;

@Slf4j
@Component
public class TicketCommandValidator {


    public void validate(Ticket ticket, Command command) throws InvocationTargetException, IllegalAccessException {
        assertNotNull(ticket);

        log.debug("Ticket: {}", ticket);

        Method method = findMethodForCommand(ticket, command);
        method.invoke(ticket, command);
    }

    private Method findMethodForCommand(Ticket ticket, Command command) {
        return Arrays.stream(ticket.getClass().getDeclaredMethods())
                .filter(m -> m.isAnnotationPresent(CommandValidator.class))
                // filter for params in method signature
                .filter(m -> Arrays.stream(m.getParameterTypes()).anyMatch(parameterType -> parameterType.isInstance(command)) || command == null)
                .findAny()
                .orElseThrow(() -> new AggregateRootValidationException(AggregateRootValidationMsg.TICKET_HANDLE_COMMAND_INVALID));

    }

    private void assertNotNull(Ticket ticket) {
        if (ticket == null) {
            throw new AggregateRootValidationException(AggregateRootValidationMsg.TICKET_MUST_NOT_BE_EMPTY);
        }
    }

}
