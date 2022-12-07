package ch.zuegi.ordermgmt.feature.ticket.domain.event;

import ch.zuegi.ordermgmt.feature.ticket.domain.event.logger.TicketPositionCreatedLogger;
import ch.zuegi.ordermgmt.feature.ticket.domain.vo.TicketId;
import ch.zuegi.ordermgmt.feature.ticket.domain.vo.TicketPositionId;
import ch.zuegi.ordermgmt.feature.ticket.domain.vo.TradeItemId;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;

class TicketPositionCreatedLoggerUnitTest {


    @Test
    void createTicketPositionCreatedLogEvent() {
        TicketPositionCreatedEvent ticketPositionCreated = TicketPositionCreatedEvent.builder()
                .ticketPositionId(new TicketPositionId())
                .ticketId(new TicketId())
                .tradeItemId(new TradeItemId())
                .menge(BigDecimal.TEN)
                .build();
        TicketPositionCreatedLogger logger = new TicketPositionCreatedLogger();
        logger.handle(ticketPositionCreated);
    }

}
