package ch.zuegi.ordermgmt.feature.ticket.domain;

public class TicketPositionIdStartWithException extends RuntimeException{
    public TicketPositionIdStartWithException(String s) {
        super(s);
    }
}
