package ch.zuegi.ordermgmt.shared;

import lombok.extern.slf4j.Slf4j;

import java.util.ArrayList;
import java.util.List;

@Slf4j
public class DomainEventPublisher implements DomainEventBus {

    private static DomainEventPublisher instance;

    private final  List<DomainEventSubscriber> domainEventSubscriberList = new ArrayList<>();


    // static block, damit im Fehlerfall eine Exception geworfen werden kann
    static {
        try {
            instance = new DomainEventPublisher();
        } catch (Exception e) {
            throw new RuntimeException("Exception occurred in creating domain event publisher");
        }
    }

    public static DomainEventPublisher instance() {
        return instance;
    }


    public void reset() {
            this.domainEventSubscriberList.clear();
    }

    @Override
    public void subscribe(DomainEventSubscriber domainEventSubscriber) {
        this.domainEventSubscriberList.add(domainEventSubscriber);
    }

    @Override
    public void unsubscribe(DomainEventSubscriber domainEventSubscriber) {
        this.domainEventSubscriberList.remove(domainEventSubscriber);
    }

    @Override
    public void publish(DomainEvent<?> event) {
        domainEventSubscriberList.stream()
                .filter(subscriber -> !subscriber.supports().equals(event.getClass()))
                .forEach(subscriber -> subscriber.handle(event));
    }

    @Override
    public List<DomainEventSubscriber> getDomainEventSubscribers() {
        return domainEventSubscriberList;
    }
}
