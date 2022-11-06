package ch.zuegi.ordermgmt.feature.vaughn.ticket.domain.shared;

import java.time.LocalDateTime;

public interface DomainEvent<E> {

    public int eventVersion();

    public LocalDateTime occurredOn();

}
