package ch.zuegi.ordermgmt.shared.aggregateRoot;

public interface AggregateRootValidationMsg {


    String AGGREGATE_ID_MUST_NOT_BE_NULL = "AggregateId must not be null value";
    String WRONG_AGGREGATE_TYPE_FOR_PREFIX = "AggregateId Prefix belongs to wrong Aggregate Type";
    String CURRENT_AGGREGATE_LIFECYCLE_STATE_IS_FINAL = "Current Aggregate Lifecycle State is final";
    String AGGREGATE_LIFECYCLE_STATE_NOT_ALLOWED = "Aggregate Lifecycle State not allowed";
    String TICKET_COMMAND_TICKET_POSITION_SET_MUST_NOT_BE_EMPTY = "CreateTicketPositionCommand must not be empty or null in CreateTicket command";
    String TICKET_COMMAND_MUST_NOT_BE_EMPTY = "CreateTicketCommand must not be empty or null";
    String TICKET_MUST_NOT_BE_EMPTY = "Ticket object muss noct be empty";
    String TICKET_HANDLE_COMMAND_INVALID = "Ticket Command is not valid";
    String ADD_TICKET_POSITION_COMMAND_MUST_NOT_BE_EMTPY = "AddTicketPostionCommand must not be empty or null";
    String WRONG_TICKET_FOR_TICKET_POSITION = "Wrong Ticket for this TicketPosition";
}
