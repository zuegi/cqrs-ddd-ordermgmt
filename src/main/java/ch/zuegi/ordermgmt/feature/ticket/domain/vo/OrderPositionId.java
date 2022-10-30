package ch.zuegi.ordermgmt.feature.ticket.domain.vo;

import ch.zuegi.ordermgmt.shared.EventPrefix;
import ch.zuegi.ordermgmt.shared.RandomUUID;

public class OrderPositionId extends RandomUUID {

    public OrderPositionId() {
        super();
    }

    public OrderPositionId(String id) {
        super(id);
    }

    @Override
    protected String getPrefix() {
        return EventPrefix.OPP.name()+ "-%s";
    }
}
