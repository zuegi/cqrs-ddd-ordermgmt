package ch.zuegi.ordermgmt.shared;

import io.vavr.control.Either;

public interface CommandValidation<C extends Command> {

    Either<CommandFailure, C> acceptOrReject(C command);
}
