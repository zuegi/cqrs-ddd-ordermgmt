package ch.zuegi.ordermgmt.feature.vaughn.ticket.domain;

public class TicketIdStartWithException extends RuntimeException {
    public TicketIdStartWithException(String s) {
        super(s);
    }
}
