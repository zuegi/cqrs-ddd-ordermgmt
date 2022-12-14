package ch.zuegi.ordermgmt.feature.ticket.domain.vo;

import ch.zuegi.ordermgmt.shared.EventPrefix;
import ch.zuegi.ordermgmt.shared.RandomUUID;
import lombok.EqualsAndHashCode;
import lombok.ToString;

@EqualsAndHashCode
@ToString
public class TradeItemId extends RandomUUID {

    public TradeItemId() {
        super();
    }

    public TradeItemId(String id) {
        super(id);
    }

    @Override
    protected String getPrefix() {
        return EventPrefix.TIP.name();
    }
}
