package ch.zuegi.ordermgmt.feature.ticket.domain.handler;

import ch.zuegi.ordermgmt.feature.ticket.domain.entity.TicketPositionView;
import ch.zuegi.ordermgmt.feature.ticket.domain.entity.TicketView;
import ch.zuegi.ordermgmt.feature.ticket.domain.event.TicketConfirmedEvent;
import ch.zuegi.ordermgmt.feature.ticket.domain.event.TicketCreatedEvent;
import ch.zuegi.ordermgmt.feature.ticket.domain.event.TicketPositionAddedEvent;
import ch.zuegi.ordermgmt.feature.ticket.domain.vo.TicketId;
import ch.zuegi.ordermgmt.feature.ticket.infrastructure.persistence.TicketViewRepository;
import ch.zuegi.ordermgmt.shared.DomainEvent;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

@Slf4j
@AllArgsConstructor
@Component
public class TicketViewHandler {

    private TicketViewRepository ticketViewRepository;

    @EventListener
    public void handle(DomainEvent<?, TicketId> domainEvent) {

        if (domainEvent instanceof TicketCreatedEvent ticketCreatedEvent) {
            TicketView ticketView = new TicketView();
            ticketView.setTicketId(ticketCreatedEvent.getTicketId());
            ticketView.setLocalDateTime(ticketCreatedEvent.getLocalDateTime());
            ticketView.setLifeCycleState(ticketCreatedEvent.getLifeCycleState());
            ticketViewRepository.save(ticketView);
        }
        if (domainEvent instanceof TicketPositionAddedEvent ticketPositionAddedEvent) {
            TicketView ticketView = ticketViewRepository.findByTicketId(ticketPositionAddedEvent.getTicketId());
            TicketPositionView ticketPositionView = new TicketPositionView();
            ticketPositionView.setTicketPositionId(ticketPositionAddedEvent.getTicketPositionId());
            ticketPositionView.setTradeItemId(ticketPositionAddedEvent.getTradeItemId());
            ticketPositionView.setMenge(ticketPositionAddedEvent.getMenge());
            ticketPositionView.setTicketView(ticketView);
            ticketView.getTicketPositionViewSet().add(ticketPositionView);
            ticketViewRepository.save(ticketView);

        }

        if (domainEvent instanceof TicketConfirmedEvent ticketConfirmedEvent) {
            TicketView ticketView = ticketViewRepository.findByTicketId(ticketConfirmedEvent.getTicketId());
            ticketView.setLifeCycleState(ticketConfirmedEvent.getTicketLifeCycleState());
            ticketViewRepository.save(ticketView);
        }

    }


}
