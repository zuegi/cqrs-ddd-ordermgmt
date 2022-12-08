package ch.zuegi.ordermgmt.feature.ticket.domain.handler;

import ch.zuegi.ordermgmt.feature.ticket.domain.Ticket;
import ch.zuegi.ordermgmt.shared.Command;
import ch.zuegi.ordermgmt.shared.annotation.CommandHandler;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

@Slf4j
@Component
public class TicketCommandHandler {


    public void handle(Ticket ticket, Command command) throws InvocationTargetException, IllegalAccessException {

        log.info("TicketObject in handleCommand: " +ticket);

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
