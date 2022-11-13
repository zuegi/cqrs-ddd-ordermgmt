package ch.zuegi.ordermgmt.shared;

import java.util.ArrayList;
import java.util.List;

public class DomainEventPublisher {

    private static DomainEventPublisher instance;
    private final List<DomainEventSubscriber> domainEventSubscriberList = new ArrayList<>();


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

    public <T> void publish(T domainEvent) {
        this.domainEventSubscriberList
                .forEach(domainEventSubscriber -> domainEventSubscriber.handleEvent(domainEvent));
    }
    public <T> void subscribe(DomainEventSubscriber<T> domainEventSubscriber) {
        this.domainEventSubscriberList.add(domainEventSubscriber);
    }

    public <T> void unsubscribe(DomainEventSubscriber<T> domainEventSubscriber) {
        this.domainEventSubscriberList.remove(domainEventSubscriber);
    }


    public void reset() {
            this.domainEventSubscriberList.clear();
    }
}
