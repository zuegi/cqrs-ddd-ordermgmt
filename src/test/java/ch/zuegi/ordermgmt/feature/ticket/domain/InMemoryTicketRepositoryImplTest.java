package ch.zuegi.ordermgmt.feature.ticket.domain;

import ch.zuegi.ordermgmt.feature.ticket.infrastructure.persistence.InMemoryTicketRepositoryImpl;
import ch.zuegi.ordermgmt.feature.ticket.domain.entity.Ticket;
import ch.zuegi.ordermgmt.feature.ticket.domain.entity.TicketLifeCycleState;
import ch.zuegi.ordermgmt.feature.ticket.domain.entity.TicketPosition;
import ch.zuegi.ordermgmt.feature.ticket.domain.event.TicketCreatedEvent;
import ch.zuegi.ordermgmt.feature.ticket.domain.event.TicketPositionAddedEvent;
import ch.zuegi.ordermgmt.feature.ticket.domain.event.TicketPositionRemovedEvent;
import ch.zuegi.ordermgmt.feature.ticket.domain.vo.TicketId;
import ch.zuegi.ordermgmt.feature.ticket.domain.vo.TicketPositionId;
import ch.zuegi.ordermgmt.feature.ticket.domain.vo.TradeItemId;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Optional;

import static org.assertj.core.api.AssertionsForClassTypes.tuple;

class InMemoryTicketRepositoryImplTest {


    private InMemoryTicketRepositoryImpl repository;

    @BeforeEach
    void setup() {
        repository = new InMemoryTicketRepositoryImpl();
    }

    @Test
    void remove_ticket_position_event() {
        // given
        TicketId ticketId = new TicketId();
        LocalDateTime now = LocalDateTime.now();
        TicketCreatedEvent ticketCreatedEvent = TicketTestHelper.createTicketCreatedEvent(ticketId, now);

        TicketPositionId ticketPositionId1 = new TicketPositionId();
        TradeItemId tradeItemId1 = new TradeItemId();
        TicketPositionAddedEvent ticketPositionAddedEvent1 = TicketTestHelper.createTicketPositionAddedEvent(ticketId, ticketPositionId1, tradeItemId1, BigDecimal.ONE);

        TicketPositionId ticketPositionId2 = new TicketPositionId();
        TradeItemId tradeItemId2 = new TradeItemId();
        TicketPositionAddedEvent ticketPositionAddedEvent2 = TicketTestHelper.createTicketPositionAddedEvent(ticketId, ticketPositionId2, tradeItemId2, BigDecimal.valueOf(2l));

        repository.save(ticketCreatedEvent);
        repository.save(ticketPositionAddedEvent1);
        repository.save(ticketPositionAddedEvent2);
        Ticket ticket = repository.findByTicketId(ticketId).get();
        Assertions.assertThat(ticket).isNotNull();
        Assertions.assertThat(ticket.getTicketPositionList())
                .isNotNull()
                .hasSize(2);

        TicketPositionRemovedEvent ticketPositionRemovedEvent = TicketPositionRemovedEvent.builder()
                .ticketId(ticketId)
                .ticketPositionId(ticketPositionId1)
                .build();

        // when
        repository.save(ticketPositionRemovedEvent);

        // then
        Ticket ticketWithRemovedPos = repository.findByTicketId(ticketId).get();
        Assertions.assertThat(ticketWithRemovedPos.getTicketPositionList())
                .isNotNull()
                .hasSize(1)
                .extracting(TicketPosition::getTicketId, TicketPosition::id, TicketPosition::getTradeItemId, TicketPosition::getMenge)
                .contains(
                        tuple(ticketId, ticketPositionId2, tradeItemId2, BigDecimal.valueOf(2L))
                );
        ;

    }

    @Test
    void save_ticket_position_event() {
        // given
        TicketId ticketId = new TicketId();
        LocalDateTime now = LocalDateTime.now();

        TicketCreatedEvent ticketCreatedEvent = TicketTestHelper.createTicketCreatedEvent(ticketId, now);
        repository.save(ticketCreatedEvent);

        TicketPositionId ticketPositionId1 = new TicketPositionId();
        TradeItemId tradeItemId1 = new TradeItemId();
        TicketPositionId ticketPositionId2 = new TicketPositionId();
        TradeItemId tradeItemId2 = new TradeItemId();

        TicketPositionAddedEvent ticketPositionAddedEvent1 = TicketTestHelper.createTicketPositionAddedEvent(ticketId, ticketPositionId1, tradeItemId1, BigDecimal.ONE);
        TicketPositionAddedEvent ticketPositionAddedEvent2 = TicketTestHelper.createTicketPositionAddedEvent(ticketId, ticketPositionId2, tradeItemId2, BigDecimal.valueOf(2l));

        // when
        repository.save(ticketPositionAddedEvent1);
        repository.save(ticketPositionAddedEvent2);

        // then
        Ticket ticket = repository.findByTicketId(ticketId).get();

        Assertions.assertThat(ticket).isNotNull();
        Assertions.assertThat(ticket.getTicketPositionList())
                .isNotNull()
                .hasSize(2)
                .extracting(TicketPosition::getTicketId, TicketPosition::id, TicketPosition::getTradeItemId, TicketPosition::getMenge)
                .contains(
                        tuple(ticketId, ticketPositionId1, tradeItemId1, BigDecimal.ONE),
                        tuple(ticketId, ticketPositionId2, tradeItemId2, BigDecimal.valueOf(2L))
                );
    }


    @Test
    void save_ticket_created_event() {
        // given
        TicketId ticketId = new TicketId();
        LocalDateTime now = LocalDateTime.now();

        TicketCreatedEvent ticketCreatedEvent = TicketTestHelper.createTicketCreatedEvent(ticketId, now);

        // when
        repository.save(ticketCreatedEvent);

        // then
        Optional<Ticket> byTicketId = repository.findByTicketId(ticketId);
        Ticket ticket = byTicketId.get();

        Assertions.assertThat(ticket).isNotNull()
                .extracting(Ticket::id, Ticket::getLocalDateTime, Ticket::getTicketLifeCycleState)
                .contains(ticketId, now, TicketLifeCycleState.TICKET_CREATED);
    }


}
