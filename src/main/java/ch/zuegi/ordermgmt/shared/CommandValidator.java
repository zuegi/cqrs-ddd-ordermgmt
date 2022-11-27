package ch.zuegi.ordermgmt.shared;

public interface CommandValidator<C extends Command> {

    void validate(C command);
}
