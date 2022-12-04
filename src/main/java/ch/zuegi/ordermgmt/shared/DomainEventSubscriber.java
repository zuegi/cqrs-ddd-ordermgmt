package ch.zuegi.ordermgmt.shared;

import java.util.Set;

public interface DomainEventSubscriber {

     void handle(DomainEvent<?> domainEvent);

    Class<?> supports();
}
