package ch.zuegi.ordermgmt.feature.vaughn.ticket.domain.vo;

import ch.zuegi.ordermgmt.feature.vaughn.ticket.domain.shared.ValueObject;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

public class TicketId implements ValueObject<TicketId> {

    public static final String TICKET_PREFIX = "T";

    @NotNull
    @Size(min = 16, max = 50)
    public final String id;

    public TicketId(String id) {
        this.id = getPrefix() + id;
    }

    public String getPrefix() {
        return TICKET_PREFIX;
    }

    @Override
    public boolean sameValueAs(TicketId other) {
        return other != null && this.id.equals(other.id);
    }
}

