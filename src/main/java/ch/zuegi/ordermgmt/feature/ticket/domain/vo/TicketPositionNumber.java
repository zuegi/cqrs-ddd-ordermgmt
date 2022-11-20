package ch.zuegi.ordermgmt.feature.ticket.domain.vo;

import ch.zuegi.ordermgmt.shared.ValueObject;
import lombok.EqualsAndHashCode;
import lombok.ToString;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@EqualsAndHashCode
@ToString
public class TicketPositionNumber implements ValueObject<TicketPositionNumber> {

    public static final String TICKET_PREFIX = "P";

    @NotNull
    @Size(min = 16, max = 50)
    public final String id;

    public TicketPositionNumber(String id) {
        if (id.startsWith(TICKET_PREFIX, 0)) {
            this.id = id;
        } else {
            this.id = getPrefix() + id;
        }
    }

    public String getPrefix() {
        return TICKET_PREFIX;
    }

    @Override
    public boolean sameValueAs(TicketPositionNumber other) {
        return other != null && this.id.equals(other.id);
    }
}
