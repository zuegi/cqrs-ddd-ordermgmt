package ch.zuegi.ordermgmt.shared;

import java.util.List;

public interface DomainEventBus {
    /**
     * registers a new subscribable to this EventBus instance
     */
    void subscribe(DomainEventSubscriber domainEventSubscriber);

    void unsubscribe(DomainEventSubscriber domainEventSubscriber);

    /**
     * send the given event in this EventBus implementation to be consumed by interested subscribers
     */
    void publish(DomainEvent<?> event);

    /**
     * get the list of all the subscribers associated with this EventBus instance
     */
    List<DomainEventSubscriber> getDomainEventSubscribers();
}
