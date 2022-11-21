package ch.zuegi.ordermgmt.feature.ticket.domain.command;

import ch.zuegi.ordermgmt.feature.ticket.domain.vo.TicketPositionNumber;
import ch.zuegi.ordermgmt.feature.ticket.domain.vo.TradeItemId;
import ch.zuegi.ordermgmt.shared.Command;
import lombok.Value;

import java.math.BigDecimal;

@Value(staticConstructor = "commandOf")
public class AddTicketPositionCommand implements Command {
    TicketPositionNumber ticketPositionNumber;
    TradeItemId tradeItemId;
    BigDecimal menge;
}
