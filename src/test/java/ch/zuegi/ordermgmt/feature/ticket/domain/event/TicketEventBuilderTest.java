package ch.zuegi.ordermgmt.feature.ticket.domain.event;

import ch.zuegi.ordermgmt.feature.ticket.domain.Ticket;
import ch.zuegi.ordermgmt.feature.ticket.domain.TicketTestHelper;
import ch.zuegi.ordermgmt.feature.ticket.domain.command.CreateTicketCommand;
import ch.zuegi.ordermgmt.feature.ticket.domain.vo.TicketId;
import ch.zuegi.ordermgmt.shared.DomainEvent;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;

class TicketEventBuilderTest {


    @Test
    void build_ticket_created_event() {
        // given
        TicketId ticketId = new TicketId();
        Ticket ticket = new Ticket(ticketId);
        assert ticket.id().equals(ticketId);

        CreateTicketCommand command = TicketTestHelper.createCommandForTest(LocalDateTime.now());
        ticket.handle(command);
        // whene
        DomainEvent<TicketCreatedEvent> domainEvent = TicketEventBuilder.build(ticket, command);

        // then
        TicketCreatedEvent ticketCreatedEvent = domainEvent.getEvent();
        Assertions.assertThat(ticketCreatedEvent)
                .isNotNull()
                .extracting(TicketCreatedEvent::getTicketId, TicketCreatedEvent::getLocalDateTime, TicketCreatedEvent::getLifeCycleState)
                .contains(ticket.id(), ticket.getLocalDateTime(), ticket.getTicketLifeCycleState());
    }

}
