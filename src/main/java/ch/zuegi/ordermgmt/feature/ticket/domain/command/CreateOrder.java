package ch.zuegi.ordermgmt.feature.ticket.domain.command;

import ch.zuegi.ordermgmt.feature.ticket.domain.entity.TradeDealType;
import ch.zuegi.ordermgmt.shared.Command;
import lombok.Value;

import java.util.List;

@Value(staticConstructor = "commandOf")
public class CreateOrder implements Command {

    TradeDealType tradeDealType;

    List<CreateOrderPosition> customerOrderPositionList;

}
