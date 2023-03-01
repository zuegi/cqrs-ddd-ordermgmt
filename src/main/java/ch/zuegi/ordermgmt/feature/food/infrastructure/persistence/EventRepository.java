package ch.zuegi.ordermgmt.feature.food.infrastructure.persistence;

import ch.zuegi.ordermgmt.feature.food.domain.event.FoodCartCreatedEvent;
import ch.zuegi.ordermgmt.feature.food.shared.EventSourcing;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

@Slf4j
@Component
public class EventRepository {

    Map<UUID, Event> eventMap = new HashMap<>();

    @EventSourcing
    public void on(FoodCartCreatedEvent foodCartCreatedEvent) {
        log.info("Hello and welcome {}", foodCartCreatedEvent.foodCartId());
        // bleibt immer noch die Frage, wie dieser Event gespeichert werden soll
        // vielleicht irgendwie serialisiert/de-serializiert?
        eventMap.put(foodCartCreatedEvent.foodCartId(), null);
    }

    public Object findByTargetIdentifier() {
        // falls einen persistiertes TargetIdentifier gibt
        // Lade die serialisierten Events aus dem Store und de-serialize die Events
        // fahre die Events gegen das Aggregate mit den EventHandler Methoden nach
        // gib das Aggregate zurueck
       return null;
    }


    public record Event(UUID aggregateId) {
    }
}

