package ch.zuegi.ordermgmt.feature.ticket.domain;

import ch.zuegi.ordermgmt.feature.ticket.domain.vo.FoodCart;
import ch.zuegi.ordermgmt.feature.ticket.domain.vo.TicketId;
import ch.zuegi.ordermgmt.shared.ValueObject;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

public class FoodCartId implements ValueObject<FoodCartId> {
    public static final String TICKET_PREFIX = "T";

    @NotNull
    @Size(min = 16, max = 50)
    public final String id;

    public FoodCartId(String id) {
        this.id = getPrefix() + id;
    }

    public String getPrefix() {
        return TICKET_PREFIX;
    }

    @Override
    public boolean sameValueAs(FoodCartId other) {
        return other != null && this.id.equals(other.id);
    }
}
