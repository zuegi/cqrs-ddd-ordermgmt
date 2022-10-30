package ch.zuegi.ordermgmt.feature.ticket.domain.vo;

import ch.zuegi.ordermgmt.shared.EventPrefix;
import ch.zuegi.ordermgmt.shared.RandomUUID;

public class OrderId extends RandomUUID {

    public OrderId() {
        super();
    }

    public OrderId(String id) {
        super(id);
    }

    @Override
    protected String getPrefix() {
        return EventPrefix.OP.name()+ "-%s";
    }
}
