package ch.zuegi.ordermgmt.shared;

import java.time.LocalDateTime;

public interface DomainEvent<E> {

    public int eventVersion();

    public LocalDateTime occurredOn();

}
