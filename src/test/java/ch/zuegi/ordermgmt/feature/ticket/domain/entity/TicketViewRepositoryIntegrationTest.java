package ch.zuegi.ordermgmt.feature.ticket.domain.entity;

import ch.zuegi.ordermgmt.feature.ticket.application.AbstractIntegrationTest;
import ch.zuegi.ordermgmt.feature.ticket.domain.vo.TicketId;
import ch.zuegi.ordermgmt.feature.ticket.domain.vo.TicketPositionId;
import ch.zuegi.ordermgmt.feature.ticket.domain.vo.TradeItemId;
import ch.zuegi.ordermgmt.feature.ticket.infrastructure.persistence.TicketViewRepository;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.HashSet;

import static org.assertj.core.api.AssertionsForClassTypes.tuple;

class TicketViewRepositoryIntegrationTest extends AbstractIntegrationTest {

    @Autowired
    TicketViewRepository repository;

    @Test
    void insert_ticket_view_without_ticket_position_valid() {
        // given
        TicketId ticketId = new TicketId();
        LocalDateTime now = LocalDateTime.now();
        TicketLifeCycleState ticketConfirmed = TicketLifeCycleState.TICKET_CONFIRMED;

        TicketView ticketView = createTicketView(ticketId, now, ticketConfirmed);

        // when
        repository.save(ticketView);

        // then

        TicketView ticketViewByTicketId = repository.findByTicketId(ticketId);

        Assertions.assertThat(ticketViewByTicketId).isNotNull()
                .isInstanceOf(TicketView.class)
                .extracting(TicketView::getTicketId, TicketView::getLocalDateTime, TicketView::getLifeCycleState, TicketView::getTicketPositionViewSet)
                .contains(ticketId, now, ticketConfirmed, new HashSet<>());
    }


    @Test
    void insert_ticket_view_with_ticket_postion_valid() {
        // given
        TicketId ticketId = new TicketId();
        LocalDateTime now = LocalDateTime.now();
        TicketLifeCycleState ticketConfirmed = TicketLifeCycleState.TICKET_CONFIRMED;

        TicketPositionId ticketPositionId = new TicketPositionId();
        TradeItemId tradeItemId = new TradeItemId();
        BigDecimal ten = BigDecimal.valueOf(10.00d).setScale(2);

        TicketPositionView ticketPositionView = new TicketPositionView();
        ticketPositionView.setTicketPositionId(ticketPositionId);
        ticketPositionView.setMenge(ten);
        ticketPositionView.setTradeItemId(tradeItemId);

        TicketView ticketView = createTicketView(ticketId, now, ticketConfirmed);
        ticketPositionView.setTicketView(ticketView);
        ticketView.getTicketPositionViewSet().add(ticketPositionView);

        // when
        repository.save(ticketView);

        // then

        TicketView ticketViewByTicketId = repository.findByTicketId(ticketId);

        Assertions.assertThat(ticketViewByTicketId.ticketPositionViewSet).isNotNull()
                .hasSize(1)
                .extracting(TicketPositionView::getTicketView, TicketPositionView::getTicketPositionId, TicketPositionView::getTradeItemId, TicketPositionView::getMenge)
                .contains(
                        tuple(ticketViewByTicketId, ticketPositionId, tradeItemId, ten)
                );
    }

    private static TicketView createTicketView(TicketId ticketId, LocalDateTime now, TicketLifeCycleState ticketConfirmed) {
        TicketView ticketView = new TicketView();
        ticketView.setTicketId(ticketId);
        ticketView.setLifeCycleState(ticketConfirmed);
        ticketView.setLocalDateTime(now);
        return ticketView;
    }
}
