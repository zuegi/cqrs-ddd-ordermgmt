package ch.zuegi.ordermgmt.feature.ticket.application.ticket;

import ch.zuegi.ordermgmt.feature.ticket.application.AbstractIntegrationTest;
import ch.zuegi.ordermgmt.feature.ticket.domain.Ticket;
import ch.zuegi.ordermgmt.feature.ticket.domain.TicketTestHelper;
import ch.zuegi.ordermgmt.feature.ticket.domain.entity.TicketEntity;
import ch.zuegi.ordermgmt.feature.ticket.domain.entity.TicketLifeCycleState;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

class TicketServiceIntegrationTest extends AbstractIntegrationTest {


    @Autowired
    TicketService ticketService;


    @Test
    void createTicketOnly() {
        // given
        Ticket ticket = TicketTestHelper.ticketForTest();

        // when
        ticketService.createTicket(ticket);
        TicketEntity ticketEntity = ticketService.findByTicketNumber(ticket.id());

        // then
        Assertions.assertThat(ticketEntity).isNotNull().isInstanceOf(TicketEntity.class)
                .extracting(TicketEntity::getTicketNumber, TicketEntity::getLifeCycleState)
                .contains(ticket.id(), TicketLifeCycleState.TICKET_CREATED);

        // assertThat ticketPositionEntity, was aber nicht erfolgreich sein kann, weil es keine TicketPositionEntity geschrieben hat
        Assertions.assertThat(ticketEntity.getTicketPositionEntitySet()).isNotNull().hasSize(0);
    }

}
