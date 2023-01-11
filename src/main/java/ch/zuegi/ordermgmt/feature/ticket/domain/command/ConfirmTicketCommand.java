package ch.zuegi.ordermgmt.feature.ticket.domain.command;

import ch.zuegi.ordermgmt.feature.ticket.domain.vo.TicketId;
import ch.zuegi.ordermgmt.shared.Command;
import lombok.Builder;
import lombok.Getter;
import lombok.ToString;

@Getter
@Builder
@ToString
public class ConfirmTicketCommand implements Command {
    TicketId ticketId;
}
