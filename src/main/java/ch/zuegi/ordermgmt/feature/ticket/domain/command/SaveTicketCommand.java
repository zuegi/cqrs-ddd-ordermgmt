package ch.zuegi.ordermgmt.feature.ticket.domain.command;

import ch.zuegi.ordermgmt.feature.ticket.domain.entity.TicketLifeCycleState;
import ch.zuegi.ordermgmt.feature.ticket.domain.event.TicketPositionAddEvent;
import ch.zuegi.ordermgmt.feature.ticket.domain.vo.TicketNumber;
import ch.zuegi.ordermgmt.shared.Command;
import lombok.Value;

import java.time.LocalDateTime;
import java.util.Set;

@Value(staticConstructor = "commandOf")
public class SaveTicketCommand implements Command {

    LocalDateTime localDateTime;
    TicketLifeCycleState ticketLifeCycleState;
    Set<TicketPositionAddEvent> addTicketPositionCommandSet;

}
