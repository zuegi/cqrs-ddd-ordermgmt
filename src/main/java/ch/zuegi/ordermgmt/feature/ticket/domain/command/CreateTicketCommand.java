package ch.zuegi.ordermgmt.feature.ticket.domain.command;

import ch.zuegi.ordermgmt.feature.ticket.domain.entity.TicketLifeCycleState;
import ch.zuegi.ordermgmt.shared.Command;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;
import java.util.Set;

@Builder
@Getter
public class CreateTicketCommand implements Command {

    LocalDateTime localDateTime;
    TicketLifeCycleState ticketLifeCycleState;
    Set<CreateTicketPositionCommand> createTicketPositionCommands;

}
