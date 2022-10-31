package ch.zuegi.ordermgmt.feature.test.shared;

import ch.zuegi.ordermgmt.shared.Command;

public interface DomainCommandHandler<A, C extends Command>  {
     void handle(C command);

}
