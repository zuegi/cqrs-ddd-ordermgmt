package ch.zuegi.ordermgmt.shared;

public interface CommandHandler<C extends Command, E extends Entity> {
    E handle(Command command);
}
