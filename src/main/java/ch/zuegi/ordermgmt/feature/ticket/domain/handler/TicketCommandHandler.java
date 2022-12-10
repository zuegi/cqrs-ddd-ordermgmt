package ch.zuegi.ordermgmt.feature.ticket.domain.handler;

import ch.zuegi.ordermgmt.feature.ticket.domain.Ticket;
import ch.zuegi.ordermgmt.feature.ticket.domain.validator.TicketCommandValidator;
import ch.zuegi.ordermgmt.shared.Command;
import ch.zuegi.ordermgmt.shared.annotation.CommandHandler;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

@Slf4j
@AllArgsConstructor
@Component
public class TicketCommandHandler {

    private TicketCommandValidator ticketCommandValidator;

    public void handle(Ticket ticket, Command command) throws InvocationTargetException, IllegalAccessException {

        assert ticket != null;

        log.debug("Ticket: {}", ticket.toString());

        // validate first
        ticketCommandValidator.validate(ticket, command);

        for (Method declaredMethod : ticket.getClass().getDeclaredMethods()) {
            if (declaredMethod.isAnnotationPresent(CommandHandler.class)) {
                Class<?>[] parameterTypes = declaredMethod.getParameterTypes();
                for (Class<?> parameterType : parameterTypes) {
                    if (parameterType.isInstance(command)) {
                        declaredMethod.invoke(ticket, command);
                    }
                }
            }
        }
    }
}
