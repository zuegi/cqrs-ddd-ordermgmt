package ch.zuegi.ordermgmt.feature.ticket.domain.validator;

import ch.zuegi.ordermgmt.feature.ticket.domain.entity.Ticket;
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

        log.debug("Command: {}", command);

        Method method = findMethodForCommand(command);
        method.invoke(ticket, command);
    }

    private Method findMethodForCommand(Command command) {
        return Arrays.stream(Ticket.class.getDeclaredMethods())
                .filter(m -> m.isAnnotationPresent(CommandValidator.class))
                // filter for params in method signature
                .filter(m -> Arrays.stream(m.getParameterTypes()).anyMatch(parameterType -> parameterType.isInstance(command)))
                .findAny()
                .orElseThrow(() -> new AggregateRootValidationException(AggregateRootValidationMsg.TICKET_HANDLE_COMMAND_INVALID));

    }

}
