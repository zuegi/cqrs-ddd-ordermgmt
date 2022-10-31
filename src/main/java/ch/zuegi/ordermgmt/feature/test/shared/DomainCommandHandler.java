package ch.zuegi.ordermgmt.feature.test.shared;

import ch.zuegi.ordermgmt.shared.Command;

import javax.validation.constraints.NotNull;

public interface DomainCommandHandler<A, C extends Command>  {
     void handle(C command);

}
