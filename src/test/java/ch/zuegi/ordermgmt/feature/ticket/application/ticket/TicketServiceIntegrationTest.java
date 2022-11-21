package ch.zuegi.ordermgmt.feature.ticket.application.ticket;

import ch.zuegi.ordermgmt.feature.ticket.application.AbstractIntegrationTest;
import ch.zuegi.ordermgmt.feature.ticket.domain.DomainTest;
import ch.zuegi.ordermgmt.feature.ticket.domain.Ticket;
import ch.zuegi.ordermgmt.feature.ticket.domain.TicketTestHelper;
import ch.zuegi.ordermgmt.feature.ticket.domain.command.SaveTicketCommand;
import ch.zuegi.ordermgmt.feature.ticket.domain.entity.TicketEntity;
import ch.zuegi.ordermgmt.feature.ticket.domain.entity.TicketLifeCycleState;
import ch.zuegi.ordermgmt.feature.ticket.domain.event.TicketPositionAddEvent;
import ch.zuegi.ordermgmt.feature.ticket.domain.event.TicketSaveEvent;
import ch.zuegi.ordermgmt.feature.ticket.domain.vo.TicketNumber;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;

import static ch.zuegi.ordermgmt.feature.ticket.domain.TicketTestHelper.TICKET_ID;

class TicketServiceIntegrationTest extends AbstractIntegrationTest {


    @Autowired
    TicketService ticketService;


    @Test
    void createTicketOnly() {
        // TicketNumber ausserhalb generieren, da diese Nummer von einer anderen Domain erstellt werden könnte
        TicketNumber ticketNumber = new TicketNumber(TICKET_ID);
        LocalDateTime now = LocalDateTime.now();

        Set<TicketPositionAddEvent> ticketPositionAddedSet = new HashSet<>();

        SaveTicketCommand saveTicketCommand = SaveTicketCommand.commandOf(now, TicketLifeCycleState.TICKET_CREATED, ticketPositionAddedSet);

        // when
        ticketService.createTicket(ticketNumber, saveTicketCommand);

        // then
        // TODO QueryHandler und Queries implementieren
        TicketEntity ticketEntity = ticketService.findByTicketNumber(ticketNumber);

        Assertions.assertThat(ticketEntity).isNotNull()
                .extracting(TicketEntity::getTicketNumber, TicketEntity::getLocalDateTime, TicketEntity::getLifeCycleState)
                .contains(ticketNumber, now, TicketLifeCycleState.TICKET_CREATED);

    }

}
