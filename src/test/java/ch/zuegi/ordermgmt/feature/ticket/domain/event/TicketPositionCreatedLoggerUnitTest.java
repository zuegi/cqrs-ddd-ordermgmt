package ch.zuegi.ordermgmt.feature.ticket.domain.event;

import ch.zuegi.ordermgmt.feature.ticket.domain.vo.TicketId;
import ch.zuegi.ordermgmt.feature.ticket.domain.vo.TicketPositionId;
import ch.zuegi.ordermgmt.feature.ticket.domain.vo.TradeItemId;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.*;

class TicketPositionCreatedLoggerUnitTest {


    @Test
    void createTicketPositionCreatedLogEvent() {
        TicketPositionCreated ticketPositionCreated = TicketPositionCreated.builder()
                .ticketPositionId(new TicketPositionId())
                .ticketId(new TicketId())
                .tradeItemId(new TradeItemId())
                .menge(BigDecimal.TEN)
                .build();
        TicketPositionCreatedLogger logger = new TicketPositionCreatedLogger();
        logger.handle(ticketPositionCreated);
    }

}
