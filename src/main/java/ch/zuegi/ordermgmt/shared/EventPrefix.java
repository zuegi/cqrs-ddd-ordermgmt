package ch.zuegi.ordermgmt.shared;

import lombok.Getter;

@Getter
public enum EventPrefix {

    ORDER_TICKET_PREFIX("Order Ticket Prefix"),
    PROCESSING_ORDER_POSITION_PREFIX("Processing Order Position Prefix"),
    PROCESSING_ORDER_PREFIX("Processing Order Prefix");

    private String prefix;
    EventPrefix(String prefix) {
        this.prefix = prefix;
    }

    @Override
    public String toString() {
        return this.getPrefix();
    }
}
