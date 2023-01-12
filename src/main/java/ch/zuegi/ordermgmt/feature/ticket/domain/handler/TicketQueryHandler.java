package ch.zuegi.ordermgmt.feature.ticket.domain.handler;

import ch.zuegi.ordermgmt.feature.ticket.domain.entity.TicketPositionView;
import ch.zuegi.ordermgmt.feature.ticket.domain.entity.TicketView;
import ch.zuegi.ordermgmt.feature.ticket.domain.query.FindByTicketIdQuery;
import ch.zuegi.ordermgmt.feature.ticket.domain.response.TicketPositionViewResponse;
import ch.zuegi.ordermgmt.feature.ticket.domain.response.TicketViewResponse;
import ch.zuegi.ordermgmt.feature.ticket.infrastructure.persistence.TicketViewRepository;
import ch.zuegi.ordermgmt.shared.Query;
import ch.zuegi.ordermgmt.shared.aggregateRoot.AggregateRootValidationException;
import ch.zuegi.ordermgmt.shared.aggregateRoot.AggregateRootValidationMsg;
import ch.zuegi.ordermgmt.shared.annotation.QueryHandler;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.Set;
import java.util.stream.Collectors;

@Slf4j
@AllArgsConstructor
@Service
public class TicketQueryHandler {

    private TicketViewRepository ticketViewRepository;

    public TicketViewResponse handle(Query query) throws InvocationTargetException, IllegalAccessException {

        Method method = findMethodForQuery(query);
        return (TicketViewResponse) method.invoke(this, query);

    }

    @QueryHandler
    private TicketViewResponse handle(FindByTicketIdQuery findByTicketIdQuery) {
        TicketView ticketView = ticketViewRepository.findByTicketId(findByTicketIdQuery.getTicketId());
        return new TicketViewResponse(ticketView.getTicketId(), ticketView.getLocalDateTime(), ticketView.getLifeCycleState(), mapToTicketPositionResponseSet(ticketView.getTicketPositionViewSet()));
    }

    private Set<TicketPositionViewResponse> mapToTicketPositionResponseSet(Set<TicketPositionView> ticketPositionViewSet) {
        return ticketPositionViewSet.stream()
                .map(posi -> new TicketPositionViewResponse(posi.getTicketView().getTicketId(), posi.getTicketPositionId(),posi.getTradeItemId(), posi.getMenge()))
                .collect(Collectors.toSet());
    }

    private Method findMethodForQuery(Query query) {
        return Arrays.stream(this.getClass().getDeclaredMethods())
                .filter(m -> m.isAnnotationPresent(QueryHandler.class))
                // filter for params in method signature
                .filter(m -> Arrays.stream(m.getParameterTypes()).anyMatch(parameterType -> parameterType.isInstance(query)))
                .findAny()
                .orElseThrow(() -> new AggregateRootValidationException(AggregateRootValidationMsg.TICKET_HANDLE_EVENT_INVALID));

    }
}
