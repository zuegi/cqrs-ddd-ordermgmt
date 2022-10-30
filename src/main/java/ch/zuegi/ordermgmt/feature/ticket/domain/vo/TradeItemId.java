package ch.zuegi.ordermgmt.feature.ticket.domain.vo;

import ch.zuegi.ordermgmt.shared.EventPrefix;
import ch.zuegi.ordermgmt.shared.RandomUUID;

public class TradeItemId extends RandomUUID {

    public TradeItemId() {
        super();
    }

    public TradeItemId(String id) {
        super(id);
    }

    @Override
    protected String getPrefix() {
        return EventPrefix.TIP.name()+ "-%s";
    }
}
