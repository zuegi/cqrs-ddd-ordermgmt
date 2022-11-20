package ch.zuegi.ordermgmt.feature.ticket.domain.vo;

import ch.zuegi.ordermgmt.shared.ValueObject;
import lombok.EqualsAndHashCode;
import lombok.ToString;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@EqualsAndHashCode
@ToString
public class TicketNumber implements ValueObject<TicketNumber> {

    public static final String TICKET_PREFIX = "T";

    @NotNull
    @Size(min = 16, max = 50)
    public final String id;

    public TicketNumber(String id) {
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
    public boolean sameValueAs(TicketNumber other) {
        return other != null && this.id.equals(other.id);
    }
}

