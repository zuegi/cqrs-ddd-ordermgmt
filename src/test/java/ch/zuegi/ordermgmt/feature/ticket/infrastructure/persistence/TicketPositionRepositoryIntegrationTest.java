package ch.zuegi.ordermgmt.feature.ticket.infrastructure.persistence;

import ch.zuegi.ordermgmt.feature.ticket.application.AbstractIntegrationTest;
import ch.zuegi.ordermgmt.feature.ticket.domain.entity.TicketEntity;
import ch.zuegi.ordermgmt.feature.ticket.domain.entity.TicketLifeCycleState;
import ch.zuegi.ordermgmt.feature.ticket.domain.entity.TicketPositionEntity;
import ch.zuegi.ordermgmt.feature.ticket.domain.vo.TicketNumber;
import ch.zuegi.ordermgmt.feature.ticket.domain.vo.TicketPositionNumber;
import ch.zuegi.ordermgmt.feature.ticket.domain.vo.TradeItemId;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Optional;

import static org.assertj.core.api.AssertionsForClassTypes.tuple;

class TicketPositionRepositoryIntegrationTest extends AbstractIntegrationTest {

    @Autowired
    TicketPositionRepository repository;

    @Test
    void insert() {
        // given
        TradeItemId tradeItemId = new TradeItemId();
        String id = "1234";
        TicketNumber ticketNumber = new TicketNumber(id);
        LocalDateTime now = LocalDateTime.now();
        TicketPositionNumber ticketPositionNumber = new TicketPositionNumber(id);

        TicketEntity ticket = new TicketEntity();
        ticket.setTicketNumber(ticketNumber);
        ticket.setLocalDateTime(now);
        ticket.setLifeCycleState(TicketLifeCycleState.TICKET_CREATED);

        TicketPositionEntity ticketPosition = new TicketPositionEntity();
        ticketPosition.setTicketPositionNumber(ticketPositionNumber);
        ticketPosition.setTradeItemId(tradeItemId);
        ticketPosition.setMenge(BigDecimal.TEN);
        ticketPosition.setTicketEntity(ticket);

        // when
        TicketPositionEntity positionEntity = repository.save(ticketPosition);
        Assertions.assertThat(positionEntity).isNotNull()
                .extracting(TicketPositionEntity::getTicketPositionId)
                .isNotNull();


        TicketPositionEntity ticketPositionById = repository.findById(positionEntity.getTicketPositionId()).get();

        // then
        // TicketPositionEntity
        Assertions.assertThat(ticketPositionById)
                .extracting(TicketPositionEntity::getTicketPositionNumber, TicketPositionEntity::getTradeItemId, TicketPositionEntity::getMenge)
                .contains(ticketPositionNumber, tradeItemId, BigDecimal.TEN);

        // TicketEntity
        Assertions.assertThat(ticketPositionById.getTicketEntity()).isNotNull()
                .extracting(TicketEntity::getTicketNumber, TicketEntity::getLifeCycleState, TicketEntity::getLocalDateTime)
                .contains(ticketNumber, TicketLifeCycleState.TICKET_CREATED, now);

    }

}
