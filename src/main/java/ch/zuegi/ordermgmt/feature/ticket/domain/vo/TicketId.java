package ch.zuegi.ordermgmt.feature.ticket.domain.vo;

import ch.zuegi.ordermgmt.shared.EventPrefix;
import ch.zuegi.ordermgmt.shared.RandomUUID;

public class TicketId extends RandomUUID {

    public TicketId() {
        super();
    }

    public TicketId(String id) {
        super(id);
    }

    @Override
    protected String getPrefix() {
        return EventPrefix.ORDER_TICKET_PREFIX.name()+ "-%s";
    }
}
