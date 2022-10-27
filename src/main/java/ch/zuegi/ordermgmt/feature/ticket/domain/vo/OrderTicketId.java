package ch.zuegi.ordermgmt.feature.ticket.domain.vo;

import ch.zuegi.ordermgmt.shared.EventPrefix;
import ch.zuegi.ordermgmt.shared.RandomUUID;

public class OrderTicketId extends RandomUUID {

    public OrderTicketId() {
        super();
    }

    public OrderTicketId(String id) {
        super(id);
    }

    @Override
    protected String getPrefix() {
        return EventPrefix.ORT.name()+ "-%s";
    }
}
