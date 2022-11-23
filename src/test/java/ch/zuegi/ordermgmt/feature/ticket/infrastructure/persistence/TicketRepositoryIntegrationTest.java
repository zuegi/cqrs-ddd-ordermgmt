package ch.zuegi.ordermgmt.feature.ticket.infrastructure.persistence;

import ch.zuegi.ordermgmt.feature.ticket.application.AbstractIntegrationTest;
import ch.zuegi.ordermgmt.feature.ticket.domain.entity.TicketEntity;
import ch.zuegi.ordermgmt.feature.ticket.domain.entity.TicketLifeCycleState;
import ch.zuegi.ordermgmt.feature.ticket.domain.entity.TicketPositionEntity;
import ch.zuegi.ordermgmt.feature.ticket.domain.vo.TicketNumber;
import ch.zuegi.ordermgmt.feature.ticket.domain.vo.TicketPositionNumber;
import ch.zuegi.ordermgmt.feature.ticket.domain.vo.TradeItemId;
import org.assertj.core.api.Assertions;
import org.assertj.core.groups.Tuple;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import static org.assertj.core.api.AssertionsForClassTypes.tuple;

class TicketRepositoryIntegrationTest extends AbstractIntegrationTest {

    @Autowired
    TicketRepository ticketRepository;

    @Test
    void insert() {
        // given
        TradeItemId tradeItemId = new TradeItemId();
        String id = "1234";
        TicketNumber ticketNumber = new TicketNumber(id);
        LocalDateTime now = LocalDateTime.now();
        TicketPositionNumber ticketPositionNumber = new TicketPositionNumber(id);

        TicketPositionEntity ticketPosition = new TicketPositionEntity();
        ticketPosition.setTicketPositionNumber(ticketPositionNumber);
        ticketPosition.setTradeItemId(tradeItemId);
        ticketPosition.setMenge(BigDecimal.TEN);

        TicketEntity ticket = new TicketEntity();
        ticket.setTicketNumber(ticketNumber);
        ticket.setLocalDateTime(now);
        ticket.setLifeCycleState(TicketLifeCycleState.TICKET_CREATED);
        ticket.getTicketPositionEntitySet().add(ticketPosition);

        // when
        TicketEntity savedTicket = ticketRepository.save(ticket);

        // then
        TicketEntity ticketEntityDB = ticketRepository.findByTicketNumber(ticketNumber);

        Assertions.assertThat(ticketEntityDB).isNotNull()
                .extracting(TicketEntity::getTicketNumber, TicketEntity::getLifeCycleState)
                .contains(ticketNumber, TicketLifeCycleState.TICKET_CREATED);


        Assertions.assertThat(ticketEntityDB.getTicketPositionEntitySet()).isNotNull().hasSize(1)
                .extracting(TicketPositionEntity::getTicketEntity, TicketPositionEntity::getTicketPositionNumber, TicketPositionEntity::getTradeItemId, TicketPositionEntity::getMenge)
                .contains(
                        tuple(null, ticketPositionNumber, tradeItemId, BigDecimal.TEN)
                );
    }
}
