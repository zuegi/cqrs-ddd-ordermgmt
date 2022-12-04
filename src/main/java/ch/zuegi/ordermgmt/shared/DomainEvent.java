package ch.zuegi.ordermgmt.shared;

import java.time.LocalDateTime;

public interface DomainEvent<E> {

    E getEvent();

    int eventVersion();

    LocalDateTime occurredOn();

}
