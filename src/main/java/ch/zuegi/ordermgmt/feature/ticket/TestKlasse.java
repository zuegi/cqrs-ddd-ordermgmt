package ch.zuegi.ordermgmt.feature.ticket;

import ch.zuegi.ordermgmt.feature.ticket.domain.Ticket;
import ch.zuegi.ordermgmt.feature.ticket.domain.TicketDomainHandler;
import ch.zuegi.ordermgmt.feature.ticket.domain.command.CreateTicketCommand;
import ch.zuegi.ordermgmt.feature.ticket.domain.command.CreateTicketPositionCommand;
import ch.zuegi.ordermgmt.feature.ticket.domain.entity.TicketLifeCycleState;
import ch.zuegi.ordermgmt.feature.ticket.domain.validator.TicketDomainValidator;
import ch.zuegi.ordermgmt.feature.ticket.domain.vo.TradeItemId;
import ch.zuegi.ordermgmt.shared.DomainHandler;
import ch.zuegi.ordermgmt.shared.DomainValidator;
import ch.zuegi.ordermgmt.shared.aggregateRoot.AggregateRootValidationException;
import ch.zuegi.ordermgmt.shared.aggregateRoot.AggregateRootValidationMsg;

import java.lang.reflect.InvocationTargetException;
import java.math.BigDecimal;
import java.time.LocalDateTime;

public class TestKlasse {

    public static void main(String[] args) throws InvocationTargetException, IllegalAccessException {
        CreateTicketCommand createTicketCommand = CreateTicketCommand.builder().ticketLifeCycleState(TicketLifeCycleState.TICKET_CREATED).localDateTime(LocalDateTime.now()).build();
        TicketDomainHandler ticketCommandHandler = new TicketDomainHandler();
        ticketCommandHandler.handle(createTicketCommand);

        CreateTicketPositionCommand createTicketPositionCommand = CreateTicketPositionCommand.builder().menge(BigDecimal.TEN).tradeItemId(new TradeItemId()).build();
        ticketCommandHandler.handle(createTicketPositionCommand);

    }

}
