package ch.zuegi.ordermgmt.shared.aggregateRoot;

public interface AggregateRootValidationMsg {


    String AGGREGATE_ID_MUST_NOT_BE_NULL = "AggregateId must not be null value";
    String WRONG_AGGREGATE_TYPE_FOR_PREFIX = "AggregateId Prefix belongs to wrong Aggregate Type";
    String AGGREGATE_COMMAND_MUST_NOT_BE_NULL = "Aggregate Command must not be null";
    String CURRENT_AGGREGATE_LIFECYCLE_STATE_IS_FINAL = "Current Aggregate Lifecycle State is final";
    String AGGREGATE_LIFECYCLE_STATE_NOT_ALLOWED = "Aggregate Lifecycle State not allowed";
    String WRONG_COMMAND_TYPE = "Wrong Command Type";
    String TICKET_COMMAND_TICKET_POSITION_SET_MUST_NOT_BE_EMPTY = "CreateTicketPositionCommand must not be empty or null in CreateTicket command";
    String TICKET_COMMAND_MUST_NOT_BE_EMPTY = "CreateTicketCommand must not be empty or null";
}
