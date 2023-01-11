package ch.zuegi.ordermgmt.feature.ticket.domain.entity;

import lombok.Getter;

import java.util.Optional;

@Getter
public enum TicketLifeCycleState {
    // Keep the order/position of the enums
    TICKET_CREATED("Ticket created"),
    TICKET_CONFIRMED("Ticket confirmed"),
    TICKET_IN_PROCESSING("Ticket in processing"),
    TICKET_IS_PREPARED("Ticket will be prepared"),
    TICKET_PROCESSED("Ticket processed");

    private final String messageKey;
    private TicketLifeCycleState previousTicketLifeCycleState = null;
    private TicketLifeCycleState nextTicketLifeCycleState = null;

    static {
        TicketLifeCycleState[] values = TicketLifeCycleState.values();
        for (int i = 1; i < values.length; i++) {
            values[i].previousTicketLifeCycleState = values[i - 1];
        }
        for (int i = 0; i < values.length - 1; i++) {
            values[i].nextTicketLifeCycleState = values[i + 1];
        }
    }


    TicketLifeCycleState(String message) {
        this.messageKey = message;
    }

    public Optional<TicketLifeCycleState> prev() {
        return Optional.ofNullable(previousTicketLifeCycleState);
    }

    public Optional<TicketLifeCycleState> next() {
        return Optional.ofNullable(nextTicketLifeCycleState);
    }
}
