package ch.zuegi.ordermgmt.feature.ticket.domain.command;

import ch.zuegi.ordermgmt.feature.ticket.domain.OrderTicketEntity;
import ch.zuegi.ordermgmt.shared.Command;
import ch.zuegi.ordermgmt.shared.CommandHandler;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class CreateOrderTicketCommandHandler implements CommandHandler<CreateOrderTicket, OrderTicketEntity> {


    @Override
    public OrderTicketEntity handle(Command command) {
        log.info("{} called with {}", this.getClass().getName(), command);
        return null;
    }
}
