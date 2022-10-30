package ch.zuegi.ordermgmt.shared;

public interface CommandHandler<C extends Command, E extends Entity<ID>, ID >{
    E handle(ID aggregateId, C command);

}
