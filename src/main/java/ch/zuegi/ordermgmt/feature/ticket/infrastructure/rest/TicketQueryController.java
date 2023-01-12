package ch.zuegi.ordermgmt.feature.ticket.infrastructure.rest;

import ch.zuegi.ordermgmt.feature.ticket.domain.entity.TicketView;
import ch.zuegi.ordermgmt.feature.ticket.domain.handler.TicketQueryHandler;
import ch.zuegi.ordermgmt.feature.ticket.domain.query.FindByTicketIdQuery;
import ch.zuegi.ordermgmt.feature.ticket.domain.response.TicketViewResponse;
import ch.zuegi.ordermgmt.feature.ticket.domain.vo.TicketId;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.lang.reflect.InvocationTargetException;

@Slf4j
@AllArgsConstructor
@RestController
@RequestMapping(value = "/api/ticket")
public class TicketQueryController {

    private TicketQueryHandler ticketQueryHandler;

    @GetMapping
    public ResponseEntity<Object> queryForTicketId(@RequestBody @Valid FindByTicketIdQuery findByTicketIdQuery) {
        try {
            ticketQueryHandler.handle(findByTicketIdQuery);
        } catch (InvocationTargetException | IllegalAccessException e) {
            // TODO korrekte Implementierung einer Exception.....
            throw new RuntimeException(e);
        }

        return ResponseEntity.ok().build();

    }

    // Same thing as above except PathVariable instead of a command
    @GetMapping("{ticketId}")
    public ResponseEntity<TicketViewResponse> queryForTicketId(@PathVariable String ticketId) {
        try {
            TicketViewResponse ticketView = ticketQueryHandler.handle(new FindByTicketIdQuery(new TicketId(ticketId)));

            return ResponseEntity.ok(ticketView);
        } catch (InvocationTargetException | IllegalAccessException e) {
            // TODO korrekte Implementierung einer Exception.....
            throw new RuntimeException(e);
        }
    }

}
