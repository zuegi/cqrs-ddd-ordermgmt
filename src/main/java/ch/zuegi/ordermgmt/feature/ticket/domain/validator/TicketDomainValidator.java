package ch.zuegi.ordermgmt.feature.ticket.domain.validator;

import ch.zuegi.ordermgmt.feature.ticket.domain.Ticket;
import ch.zuegi.ordermgmt.feature.ticket.domain.command.CreateTicketCommand;
import ch.zuegi.ordermgmt.feature.ticket.domain.command.CreateTicketPositionCommand;
import ch.zuegi.ordermgmt.feature.ticket.domain.command.UpdateTicketLifecycleCommand;
import ch.zuegi.ordermgmt.feature.ticket.domain.vo.TicketId;
import ch.zuegi.ordermgmt.shared.Command;
import ch.zuegi.ordermgmt.shared.DomainHandler;
import ch.zuegi.ordermgmt.shared.DomainValidator;
import ch.zuegi.ordermgmt.shared.aggregateRoot.AggregateRootValidationException;
import lombok.extern.slf4j.Slf4j;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

@Slf4j
public class TicketDomainValidator {

    private Ticket ticket;

    public TicketDomainValidator(Ticket ticket) {
        this.ticket = ticket;
    }

    public void validate(Command command) throws InvocationTargetException, IllegalAccessException {
        log.info("TicketObject in validateCommand: " +ticket);

        for (Method declaredMethod : ticket.getClass().getDeclaredMethods()) {
            if (declaredMethod.isAnnotationPresent(DomainValidator.class)) {
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
