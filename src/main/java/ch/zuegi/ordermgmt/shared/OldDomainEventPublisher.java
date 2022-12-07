package ch.zuegi.ordermgmt.shared;

import lombok.extern.slf4j.Slf4j;

import java.util.ArrayList;
import java.util.List;

@Slf4j
public class OldDomainEventPublisher implements DomainEventBus {

    private static OldDomainEventPublisher instance;

    private final  List<DomainEventSubscriber> domainEventSubscriberList = new ArrayList<>();


    // static block, damit im Fehlerfall eine Exception geworfen werden kann
    static {
        try {
            instance = new OldDomainEventPublisher();
        } catch (Exception e) {
            throw new RuntimeException("Exception occurred in creating domain event publisher");
        }
    }

    public static OldDomainEventPublisher instance() {
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
                // hier weiterfahren.... noch immer nicht gelöst
                .peek(t -> System.out.println("**** " +t.supports()))

                .filter(subscriber ->  subscriber.supports().contains(event.getClass()))
                .peek(subscriber -> System.out.println("class -> " +subscriber.getClass()))
                .forEach(subscriber -> subscriber.handle(event));
    }

    @Override
    public List<DomainEventSubscriber> getDomainEventSubscribers() {
        return domainEventSubscriberList;
    }
}
