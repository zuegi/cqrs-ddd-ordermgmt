package ch.zuegi.ordermgmt.shared;

import java.io.Serializable;
import java.time.LocalDateTime;

public abstract class DomainEvent<E, ID extends Serializable>  {

    public abstract E getEvent();

    public abstract int eventVersion();

    public abstract LocalDateTime occurredOn();

    public abstract ID id();
}
