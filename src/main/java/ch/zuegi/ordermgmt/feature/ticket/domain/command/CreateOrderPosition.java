package ch.zuegi.ordermgmt.feature.ticket.domain.command;

import ch.zuegi.ordermgmt.feature.ticket.domain.vo.TradeItemId;
import ch.zuegi.ordermgmt.shared.Command;
import lombok.Value;

import java.math.BigDecimal;

@Value(staticConstructor = "commandOf")
public class CreateOrderPosition implements Command {
    
    TradeItemId tradeItemId;
    BigDecimal menge;

}
