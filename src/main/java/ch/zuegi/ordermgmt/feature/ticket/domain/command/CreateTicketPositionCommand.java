package ch.zuegi.ordermgmt.feature.ticket.domain.command;

import ch.zuegi.ordermgmt.feature.ticket.domain.vo.TradeItemId;
import ch.zuegi.ordermgmt.shared.Command;
import lombok.Builder;
import lombok.Data;
import lombok.ToString;

import java.math.BigDecimal;

@Data
@Builder
@ToString
public class CreateTicketPositionCommand implements Command {
    TradeItemId tradeItemId;
    BigDecimal menge;
}
