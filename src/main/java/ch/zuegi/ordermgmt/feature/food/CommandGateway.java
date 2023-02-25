package ch.zuegi.ordermgmt.feature.food;

import ch.zuegi.ordermgmt.shared.annotation.CommandHandler;
import org.springframework.stereotype.Component;

import java.lang.reflect.InvocationTargetException;

@Component
public class CommandGateway {

    public void send(Object object) {

        // wie mache ich hier aus diesem Object ein spezifisches Command
        // und wie erkenne ich, dass dieses Command zu einem spezfischen Aggregate
        // annotiert mit @Aggregate gehört
        AggregateMethodResolver aggregateMethodResolver = new AggregateMethodResolver(CommandHandler.class);

        try {
            aggregateMethodResolver.resolve(object);
        } catch (InvocationTargetException | InstantiationException | IllegalAccessException e) {
            throw new RuntimeException(e);
        }
    }
}
