package ch.zuegi.ordermgmt.feature.ticket.domain.entity;

import lombok.Getter;

@Getter
public enum TicketLifeCycleState {
    TICKET_CREATED("Ticket created"),
    TICKET_IN_PROCESSING("Ticket in processing"),
    TICKET_IS_PREPARED("Ticket will be prepared"),
    TICKET_PROCESSED("Ticket processed");

    private String messageKey;
    TicketLifeCycleState(String message) {
        this.messageKey = message;
    }
}
