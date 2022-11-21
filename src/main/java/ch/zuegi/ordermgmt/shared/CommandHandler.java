package ch.zuegi.ordermgmt.shared;

public interface CommandHandler<C extends Command, E extends DomainEvent, ID >{
    E handle(ID aggregateId, C command);

}
