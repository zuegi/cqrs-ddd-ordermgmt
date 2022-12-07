package ch.zuegi.ordermgmt.shared;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Component;

@Component
public class DomainEventPublisher {

    @Autowired
    ApplicationEventPublisher applicationEventPublisher;

    public void publish(DomainEvent<?> event) {
        applicationEventPublisher.publishEvent(event);
    }

}
