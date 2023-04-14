package ch.zuegi.ordermgmt.feature.ticket.domain.command;

import ch.zuegi.ordermgmt.feature.ticket.domain.event.TicketPositionAddEvent;
import ch.zuegi.ordermgmt.feature.ticket.domain.event.TicketSaveEvent;
import ch.zuegi.ordermgmt.feature.ticket.domain.vo.TicketNumber;
import ch.zuegi.ordermgmt.shared.CommandHandler;
import ch.zuegi.ordermgmt.shared.DomainEventPublisher;

import java.util.HashSet;

public class SaveTicketHandlerImpl implements CommandHandler<SaveTicketCommand, TicketSaveEvent, TicketNumber> {

    @Override
    public TicketSaveEvent handle(TicketNumber aggregateId, SaveTicketCommand command) {
        // TODO validate

        // TODO calculate?

        // TODO Set< TicketPositionAdded>
        HashSet<TicketPositionAddEvent> hashSet = new HashSet<>();
        TicketSaveEvent ticketSaveEvent = TicketSaveEvent.eventOf(aggregateId, command.getLocalDateTime(), command.getTicketLifeCycleState(), hashSet);
        DomainEventPublisher
                .instance()
                .publish(ticketSaveEvent);

        return ticketSaveEvent;
    }
}
