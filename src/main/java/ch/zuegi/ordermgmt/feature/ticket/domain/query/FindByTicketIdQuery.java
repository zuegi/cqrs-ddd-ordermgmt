package ch.zuegi.ordermgmt.feature.ticket.domain.query;

import ch.zuegi.ordermgmt.feature.ticket.domain.vo.TicketId;
import ch.zuegi.ordermgmt.shared.Query;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.ToString;
import lombok.extern.jackson.Jacksonized;

@AllArgsConstructor
@Getter
@ToString
@Jacksonized
public class FindByTicketIdQuery implements Query {
    TicketId ticketId;
}
