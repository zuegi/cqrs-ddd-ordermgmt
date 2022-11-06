package ch.zuegi.ordermgmt.feature.vaughn.ticket.domain.shared;

public interface DomainEventSubscriber<T> {

    public void handleEvent(final T aDomainEvent);

    public Class<T> subscribedToEventType();
}
