package ch.zuegi.ordermgmt.feature.ticket.domain.response;

import ch.zuegi.ordermgmt.feature.ticket.domain.entity.TicketLifeCycleState;
import ch.zuegi.ordermgmt.feature.ticket.domain.entity.TicketPositionView;
import ch.zuegi.ordermgmt.feature.ticket.domain.vo.TicketId;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.ToString;
import lombok.extern.jackson.Jacksonized;

import java.time.LocalDateTime;
import java.util.Set;

@AllArgsConstructor
@Getter
@ToString
public class TicketViewResponse {
    private TicketId ticketId;
    private LocalDateTime localDateTime;
    private TicketLifeCycleState lifeCycleState;
    private Set<TicketPositionViewResponse> ticketPositionViewSet;
}
