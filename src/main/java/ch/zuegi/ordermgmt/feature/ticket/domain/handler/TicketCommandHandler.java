package ch.zuegi.ordermgmt.feature.ticket.domain.handler;

import ch.zuegi.ordermgmt.feature.ticket.domain.Ticket;
import ch.zuegi.ordermgmt.feature.ticket.domain.TicketRepository;
import ch.zuegi.ordermgmt.feature.ticket.domain.event.TicketEventBuilder;
import ch.zuegi.ordermgmt.feature.ticket.domain.validator.TicketCommandValidator;
import ch.zuegi.ordermgmt.feature.ticket.domain.vo.TicketId;
import ch.zuegi.ordermgmt.shared.Command;
import ch.zuegi.ordermgmt.shared.DomainEvent;
import ch.zuegi.ordermgmt.shared.aggregateRoot.AggregateRootValidationException;
import ch.zuegi.ordermgmt.shared.aggregateRoot.AggregateRootValidationMsg;
import ch.zuegi.ordermgmt.shared.annotation.CommandHandler;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Component;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Arrays;

@Slf4j
@AllArgsConstructor
@Component
public class TicketCommandHandler {

    private TicketCommandValidator ticketCommandValidator;
    private ApplicationEventPublisher applicationEventPublisher;
    private TicketRepository repository;

    public void handle(TicketId ticketId, Command command) throws InvocationTargetException, IllegalAccessException {

        Ticket ticket = repository.findByTicketId(ticketId).orElse(new Ticket(ticketId));

        // validate first
        assertNotNull(ticket);
        ticketCommandValidator.validate(ticket, command);

        // filter all methods annotated with CommandHandler.class and with parameter equals command
        Method method = findMethodForCommand(ticket, command);
        // call ticket.handle(command) via invoke.... -> reflection
        method.invoke(ticket, command);

        // create DomainEvent
        DomainEvent<Object> domainEvent = TicketEventBuilder.build(ticket, command);
        // finally publish event
        applicationEventPublisher.publishEvent(domainEvent);
    }

    // filtered die mit CommandHandler.class annotieren Methoden und dem entsprechenden Command
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
