package ch.zuegi.ordermgmt.shared;

import lombok.Getter;

@Getter
public enum EventPrefix {

    ORT("Order Ticket Prefix");

    private String prefix;
    EventPrefix(String prefix) {
        this.prefix = prefix;
    }

    @Override
    public String toString() {
        return this.getPrefix();
    }
}
