package ch.zuegi.ordermgmt.feature.ticket.domain.handler;

import ch.zuegi.ordermgmt.feature.ticket.domain.Ticket;
import ch.zuegi.ordermgmt.feature.ticket.domain.vo.TicketId;
import ch.zuegi.ordermgmt.shared.Command;
import ch.zuegi.ordermgmt.shared.DomainHandler;
import lombok.extern.slf4j.Slf4j;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

@Slf4j
public class TicketDomainHandler {


    public void handle(Command command) throws InvocationTargetException, IllegalAccessException {

        Ticket ticket = new Ticket(new TicketId());
        log.info("TicketObject in handleCommand: " +ticket);

        for (Method declaredMethod : ticket.getClass().getDeclaredMethods()) {
            if (declaredMethod.isAnnotationPresent(DomainHandler.class)) {
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
