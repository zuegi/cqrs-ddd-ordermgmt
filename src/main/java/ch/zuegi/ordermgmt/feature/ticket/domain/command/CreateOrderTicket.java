package ch.zuegi.ordermgmt.feature.ticket.domain.command;

import ch.zuegi.ordermgmt.feature.ticket.domain.vo.OrderTicketId;
import ch.zuegi.ordermgmt.shared.Command;
import lombok.Value;

@Value(staticConstructor = "commandOf")
public class CreateOrderTicket implements Command {
    OrderTicketId orderTicketId;
}
