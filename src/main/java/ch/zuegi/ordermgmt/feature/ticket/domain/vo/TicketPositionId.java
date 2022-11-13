package ch.zuegi.ordermgmt.feature.ticket.domain.vo;

import ch.zuegi.ordermgmt.shared.ValueObject;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

public class TicketPositionId  implements ValueObject<TicketId> {

    public static final String TICKET_PREFIX = "P";

    @NotNull
    @Size(min = 16, max = 50)
    public final String id;

    public TicketPositionId(String id) {
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