package ch.zuegi.ordermgmt.feature.ticket.domain.command;

import ch.zuegi.ordermgmt.feature.ticket.domain.entity.TicketEntity;
import ch.zuegi.ordermgmt.shared.Command;
import ch.zuegi.ordermgmt.shared.CommandHandler;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class CreateTicketCommandHandler implements CommandHandler<CreateTicket, TicketEntity> {


    @Override
    public TicketEntity handle(Command command) {
        log.info("{} called with {}", this.getClass().getName(), command);

        // TODO hier die CustomerOrderList auseinander beineln

        // OrderTicketEntity erstellen
        TicketEntity orderTicket = new TicketEntity();
        // was halt auf dieser Entity gespeicher werden muss
        // ProcessingOrder (VA)
        // ProcessingOrderPosition(VA Position)

        // Im UnitTest die CustomerOrderList erstellen

        return orderTicket;
    }
}
