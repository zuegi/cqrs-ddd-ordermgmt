package ch.zuegi.ordermgmt.feature.ticket.application.ticket;

import ch.zuegi.ordermgmt.feature.ticket.application.AbstractIntegrationTest;
import ch.zuegi.ordermgmt.feature.ticket.domain.Ticket;
import ch.zuegi.ordermgmt.feature.ticket.domain.TicketTestHelper;
import ch.zuegi.ordermgmt.feature.ticket.domain.entity.TicketEntity;
import ch.zuegi.ordermgmt.feature.ticket.domain.entity.TicketLifeCycleState;
import ch.zuegi.ordermgmt.feature.ticket.domain.event.TicketCreatedLogger;
import ch.zuegi.ordermgmt.feature.ticket.domain.event.TicketPositionCreatedLogger;
import ch.zuegi.ordermgmt.shared.DomainEventPublisher;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

class TicketServiceIntegrationTest extends AbstractIntegrationTest {


    @Autowired
    TicketService ticketService;


    @BeforeEach
    void setup() {
        DomainEventPublisher.instance().subscribe(new TicketPositionCreatedLogger());
        DomainEventPublisher.instance().subscribe(new TicketCreatedLogger());
    }


    @Test
    void createTicketOnly() {
        // given
        Ticket ticket = TicketTestHelper.ticketForTest();

        Assertions.assertThat(ticket).isNotNull();

       /* // when
        ticketService.createTicket(ticket);
        TicketEntity ticketEntity = ticketService.findByTicketNumber(ticket.id());

        // then
        Assertions.assertThat(ticketEntity).isNotNull().isInstanceOf(TicketEntity.class)
                .extracting(TicketEntity::getTicketNumber, TicketEntity::getLifeCycleState)
                .contains(ticket.id(), TicketLifeCycleState.TICKET_CREATED);

        // assertThat ticketPositionEntity, was aber nicht erfolgreich sein kann, weil es keine TicketPositionEntity geschrieben hat
        Assertions.assertThat(ticketEntity.getTicketPositionEntitySet()).isNotNull().hasSize(0);*/
    }

}
