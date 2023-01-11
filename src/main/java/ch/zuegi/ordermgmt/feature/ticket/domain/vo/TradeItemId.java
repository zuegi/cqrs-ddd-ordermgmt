package ch.zuegi.ordermgmt.feature.ticket.domain.vo;

import ch.zuegi.ordermgmt.shared.EventPrefix;
import ch.zuegi.ordermgmt.shared.RandomUUID;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.EqualsAndHashCode;
import lombok.ToString;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@EqualsAndHashCode
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
