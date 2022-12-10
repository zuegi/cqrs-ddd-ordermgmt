package ch.zuegi.ordermgmt.feature.ticket.domain.validator;

import ch.zuegi.ordermgmt.feature.ticket.domain.Ticket;
import ch.zuegi.ordermgmt.shared.Command;
import ch.zuegi.ordermgmt.shared.annotation.CommandValidator;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.List;

@Slf4j
@Component
public class TicketCommandValidator {


    public void validate(Ticket ticket, Command command) throws InvocationTargetException, IllegalAccessException {
        assert ticket != null;

        log.debug("Ticket: {}", ticket.toString());

        List<Method> methods = Arrays.stream(ticket.getClass().getDeclaredMethods())
                .filter(method -> method.isAnnotationPresent(CommandValidator.class))
                .toList();


        for (Method declaredMethod : ticket.getClass().getDeclaredMethods()) {
            if (declaredMethod.isAnnotationPresent(CommandValidator.class)) {
                Class<?>[] parameterTypes = declaredMethod.getParameterTypes();
                for (Class<?> parameterType : parameterTypes) {
                    if (command == null || parameterType.isInstance(command)) {
                            declaredMethod.invoke(ticket, command);
                    }
                }
            }
        }
    }
}
