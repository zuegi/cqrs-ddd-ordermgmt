package ch.zuegi.ordermgmt.feature.food;

import ch.zuegi.ordermgmt.shared.annotation.CommandHandler;
import io.github.classgraph.ScanResult;
import org.springframework.stereotype.Component;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.List;

@Component
public class CommandGateway {

    ScanResult scanResult;

    public CommandGateway(ScanResult scanResult) {
        this.scanResult = scanResult;
    }

    public void send(Object command) {

        // wie mache ich hier aus diesem Object ein spezifisches Command
        // und wie erkenne ich, dass dieses Command zu einem spezfischen Aggregate
        // annotiert mit @Aggregate gehört
        AggregateMethodResolver aggregateMethodResolver = new AggregateMethodResolver(scanResult, CommandHandler.class);

        try {
            // FIXME auslagern in eine Klasse/Methode
            List<Method> methods = aggregateMethodResolver.resolve(command);
            if (methods.size() == 0) {
                throw new NoAnnotatedMethodFoundException(CommandGatewayMessage.NO_WAY);
            }
            if (methods.size() > 1) {
                throw new ToManyAnnotatedMethodException(CommandGatewayMessage.TO_MANY);
            }

            Method method = methods.get(0);

            // Finde heraus ob das object eine Annotation @AggregateRootId besitzt
            // falls ja
            // repository.findBy(AggregateRoodId) <-- wie auch immer das geht an dieser Stelle`

            // falls nein

            AggregateClassResolver aggregateClassResolver = new AggregateClassResolver(method);
            Object aggregateObject = aggregateClassResolver.resolve();

            method.invoke(aggregateObject, command);



        } catch (InvocationTargetException | InstantiationException | IllegalAccessException e) {
            // FIXME Exception definieren
            throw new RuntimeException(e);
        }
    }
}
