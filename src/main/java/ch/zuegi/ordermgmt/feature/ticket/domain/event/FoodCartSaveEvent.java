package ch.zuegi.ordermgmt.feature.ticket.domain.event;

import ch.zuegi.ordermgmt.feature.ticket.domain.vo.FoodCartId;
import ch.zuegi.ordermgmt.shared.DomainEvent;
import lombok.Value;

import java.time.LocalDateTime;

@Value(staticConstructor = "eventOf")
public class FoodCartSaveEvent implements DomainEvent {

    FoodCartId foodCartId;
    @Override
    public int eventVersion() {
        return 0;
    }

    @Override
    public LocalDateTime occurredOn() {
        return null;
    }
}
