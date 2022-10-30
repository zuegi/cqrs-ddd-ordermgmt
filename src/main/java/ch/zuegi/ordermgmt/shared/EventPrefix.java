package ch.zuegi.ordermgmt.shared;

import lombok.Getter;

@Getter
public enum EventPrefix {

    OTP("Order Ticket Prefix"),
    OPP("Order Position Prefix"),
    OP("Order Prefix"),
    TIP("Trade Item Prefix");


    private final String prefix;

    EventPrefix(String prefix) {
        this.prefix = prefix;
    }

    @Override
    public String toString() {
        return this.getPrefix();
    }
}
