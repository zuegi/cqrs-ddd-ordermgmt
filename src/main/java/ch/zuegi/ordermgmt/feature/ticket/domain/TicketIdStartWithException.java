package ch.zuegi.ordermgmt.feature.ticket.domain;

public class TicketIdStartWithException extends RuntimeException {
    public TicketIdStartWithException(String s) {
        super(s);
    }
}
