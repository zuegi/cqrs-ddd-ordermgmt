package ch.zuegi.ordermgmt.feature.ticket.domain.command;

import ch.zuegi.ordermgmt.feature.ticket.domain.vo.TicketId;
import ch.zuegi.ordermgmt.shared.Command;
import lombok.Value;

import java.util.List;

@Value(staticConstructor = "commandOf")
public class CreateTicket implements Command {
    TicketId orderTicketId;
    List<CreateOrder> createCustomerOrderList;
}
