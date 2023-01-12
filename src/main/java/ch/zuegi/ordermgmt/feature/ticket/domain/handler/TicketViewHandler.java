package ch.zuegi.ordermgmt.feature.ticket.domain.handler;

import ch.zuegi.ordermgmt.feature.ticket.domain.entity.TicketPositionView;
import ch.zuegi.ordermgmt.feature.ticket.domain.entity.TicketView;
import ch.zuegi.ordermgmt.feature.ticket.domain.event.TicketConfirmedEvent;
import ch.zuegi.ordermgmt.feature.ticket.domain.event.TicketCreatedEvent;
import ch.zuegi.ordermgmt.feature.ticket.domain.event.TicketPositionAddedEvent;
import ch.zuegi.ordermgmt.feature.ticket.domain.vo.TicketId;
import ch.zuegi.ordermgmt.feature.ticket.infrastructure.persistence.TicketViewRepository;
import ch.zuegi.ordermgmt.shared.DomainEvent;
import ch.zuegi.ordermgmt.shared.aggregateRoot.AggregateRootValidationException;
import ch.zuegi.ordermgmt.shared.aggregateRoot.AggregateRootValidationMsg;
import ch.zuegi.ordermgmt.shared.annotation.EventHandler;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Arrays;

@Slf4j
@AllArgsConstructor
@Component
public class TicketViewHandler {

    private TicketViewRepository ticketViewRepository;

    @EventListener
    public void handle(DomainEvent<?, TicketId> domainEvent) throws InvocationTargetException, IllegalAccessException {
        // find the correkct method annotated with EventHandler.class and with parameter equals event
        Method method = findMethodForEvent(domainEvent);
        // call this.handle(event) via invoke
        method.invoke(this, domainEvent);
    }

    @EventHandler
    public void handle(TicketCreatedEvent ticketCreatedEvent) {
        TicketView ticketView = new TicketView();
        ticketView.setTicketId(ticketCreatedEvent.getTicketId());
        ticketView.setLocalDateTime(ticketCreatedEvent.getLocalDateTime());
        ticketView.setLifeCycleState(ticketCreatedEvent.getLifeCycleState());
        ticketViewRepository.save(ticketView);
    }

    @EventHandler
    public void handle(TicketPositionAddedEvent ticketPositionAddedEvent) {
        TicketView ticketView = ticketViewRepository.findByTicketId(ticketPositionAddedEvent.getTicketId());
        TicketPositionView ticketPositionView = new TicketPositionView();
        ticketPositionView.setTicketPositionId(ticketPositionAddedEvent.getTicketPositionId());
        ticketPositionView.setTradeItemId(ticketPositionAddedEvent.getTradeItemId());
        ticketPositionView.setMenge(ticketPositionAddedEvent.getMenge());
        ticketPositionView.setTicketView(ticketView);
        ticketView.getTicketPositionViewSet().add(ticketPositionView);
        ticketViewRepository.save(ticketView);
    }


    @EventHandler
    public void handle(TicketConfirmedEvent ticketConfirmedEvent) {
        TicketView ticketView = ticketViewRepository.findByTicketId(ticketConfirmedEvent.getTicketId());
        ticketView.setLifeCycleState(ticketConfirmedEvent.getTicketLifeCycleState());
        ticketViewRepository.save(ticketView);
    }

    private Method findMethodForEvent(DomainEvent<?, TicketId> domainEvent) {
        return Arrays.stream(this.getClass().getDeclaredMethods())
                .filter(m -> m.isAnnotationPresent(EventHandler.class))
                // filter for params in method signature
                .filter(m -> Arrays.stream(m.getParameterTypes()).anyMatch(parameterType -> parameterType.isInstance(domainEvent)))
                .findAny()
                .orElseThrow(() -> new AggregateRootValidationException(AggregateRootValidationMsg.TICKET_HANDLE_EVENT_INVALID));

    }


}
