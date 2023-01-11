package ch.zuegi.ordermgmt.feature.ticket.domain.vo;

import ch.zuegi.ordermgmt.shared.EventPrefix;
import ch.zuegi.ordermgmt.shared.RandomUUID;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.EqualsAndHashCode;
import lombok.ToString;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@EqualsAndHashCode
public class TicketId extends RandomUUID {

    public TicketId() {
        super();
    }

    public TicketId(String id) {
        super(id);
    }
    @Override
    protected String getPrefix() {
        return EventPrefix.OTP.name();
    }

}

