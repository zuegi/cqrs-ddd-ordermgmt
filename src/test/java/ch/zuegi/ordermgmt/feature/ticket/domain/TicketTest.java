package ch.zuegi.ordermgmt.feature.ticket.domain;


import ch.zuegi.ordermgmt.feature.ticket.domain.entity.TicketEntity;
import ch.zuegi.ordermgmt.feature.ticket.domain.entity.TicketLifeCycleState;
import ch.zuegi.ordermgmt.feature.ticket.domain.entity.TicketPositionEntity;
import ch.zuegi.ordermgmt.feature.ticket.domain.event.TicketCreated;
import ch.zuegi.ordermgmt.feature.ticket.domain.event.TicketLifecycleUpdated;
import ch.zuegi.ordermgmt.feature.ticket.domain.event.TicketPositionAdded;
import ch.zuegi.ordermgmt.feature.ticket.domain.vo.TicketNumber;
import ch.zuegi.ordermgmt.feature.ticket.domain.vo.TicketPositionNumber;
import ch.zuegi.ordermgmt.feature.ticket.domain.vo.TradeItemId;
import ch.zuegi.ordermgmt.shared.aggregateRoot.AggregateRootValidationException;
import ch.zuegi.ordermgmt.shared.aggregateRoot.AggregateRootValidationMsg;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.util.List;

import static ch.zuegi.ordermgmt.feature.ticket.domain.TicketTestHelper.TICKET_ID;
import static ch.zuegi.ordermgmt.feature.ticket.domain.TicketTestHelper.ticketForTest;
import static org.assertj.core.api.AssertionsForClassTypes.tuple;

class TicketTest extends DomainTest {

    @Test
    void createTicket() {
        // given
        TicketNumber ticketNumber = new TicketNumber(TICKET_ID);
        // when
        Ticket ticket = new Ticket(ticketNumber);
        Assertions.assertThat(ticket.id().id).isEqualTo("T" + TICKET_ID);
        // then
        Assertions.assertThat(ticket).isNotNull()
                .extracting(Ticket::id)
                .isEqualTo(ticketNumber);

        Assertions.assertThat(ticket.getTicketEntity())
                .isNotNull()
                .isInstanceOf(TicketEntity.class)
                .extracting(TicketEntity::getTicketNumber, TicketEntity::getLifeCycleState)
                .contains(ticket.id(), TicketLifeCycleState.TICKET_CREATED );

        // then
        expectedEvents(1);
        expectedEvent(TicketCreated.class);
    }

    @Test
    void createTicketInvalid() {
        TicketNumber ticketNumber = null;
        // when
        Assertions.assertThatExceptionOfType(AggregateRootValidationException.class)
                .isThrownBy(() -> new Ticket(ticketNumber))
                .withMessage(AggregateRootValidationMsg.AGGREGATE_ID_MUST_NOT_BE_NULL);
    }

    @Test
    void addTicketPositionToTicket() {
        // given
        Ticket ticket = ticketForTest();
        TicketPositionNumber ticketPositionId = new TicketPositionNumber(TICKET_ID);
        TradeItemId tradeItemId = new TradeItemId();
        // when
        ticket.addTicketPosition(ticketPositionId, tradeItemId, BigDecimal.TEN);
        Assertions.assertThat(ticket.getTicketEntity()).isNotNull().isInstanceOf(TicketEntity.class);
        Assertions.assertThat(ticket.getTicketEntity().getTicketPositionEntitySet())
                .isNotNull()
                .hasSize(1)
                .extracting(TicketPositionEntity::getTicketPositionNumber, TicketPositionEntity::getTicketEntity, TicketPositionEntity::getTradeItemId, TicketPositionEntity::getMenge)
                .contains(
                        tuple(ticketPositionId, ticket.getTicketEntity(), tradeItemId, BigDecimal.TEN)
                );


        // then
        expectedEvents(2);
        expectedEvent(TicketCreated.class);
        expectedEvent(TicketPositionAdded.class);

        // then
        List<TicketPositionAdded> domainEvents = (List<TicketPositionAdded>) expectedEvents(TicketPositionAdded.class, 1);

        Assertions.assertThat(domainEvents).hasSize(1)
                .extracting(TicketPositionAdded::getTicketPositionId, TicketPositionAdded::getTicketNumber, TicketPositionAdded::getTradeItemId, TicketPositionAdded::getMenge)
                .contains(
                        tuple(ticketPositionId, ticket.id(), tradeItemId, BigDecimal.TEN)
                );
    }


    @Test
    void updateTicketStatus() {
        // given
        Ticket ticket = ticketForTest();
        TicketPositionNumber ticketPositionId = new TicketPositionNumber(TICKET_ID);
        TradeItemId tradeItemId = new TradeItemId();
        ticket.addTicketPosition(ticketPositionId, tradeItemId, BigDecimal.TEN);

        // when
        ticket.updateStatus(TicketLifeCycleState.TICKET_IN_PROCESSING);

        expectedEvents(3);
        expectedEvent(TicketLifecycleUpdated.class);
        List<TicketLifecycleUpdated> domainEvents = (List<TicketLifecycleUpdated>) expectedEvents(TicketLifecycleUpdated.class, 1);
        Assertions.assertThat(domainEvents).isNotNull().hasSize(1)
                .extracting(TicketLifecycleUpdated::getTicketNumber, TicketLifecycleUpdated::getLifeCycleState)
                .contains(
                        tuple(ticket.id(), TicketLifeCycleState.TICKET_IN_PROCESSING)
                );
        ;

    }


}
