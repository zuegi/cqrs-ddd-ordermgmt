package ch.zuegi.ordermgmt.feature.ticket.domain.command;

import ch.zuegi.ordermgmt.feature.ticket.domain.entity.TicketLifeCycleState;
import ch.zuegi.ordermgmt.shared.Command;
import lombok.Builder;
import lombok.Getter;
import lombok.ToString;

import java.time.LocalDateTime;

@Builder
@Getter
@ToString
public class UpdateTicketLifecycleCommand implements Command {

    LocalDateTime localDateTime;
    TicketLifeCycleState ticketLifeCycleState;
}
