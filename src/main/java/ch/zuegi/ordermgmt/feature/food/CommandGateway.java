package ch.zuegi.ordermgmt.feature.food;

import ch.zuegi.ordermgmt.shared.annotation.CommandHandler;
import io.github.classgraph.ScanResult;
import org.springframework.stereotype.Component;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

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
            Method method = aggregateMethodResolver.resolve(command);

            // Finde heraus ob das object eine Annotation @AggregateRootId besitzt
            // falls ja
            // repository.findBy(AggregateRoodId) <-- wie auch immer das geht an dieser Stelle`

            // falls nein
//        System.out.println(method.getDeclaringClass().getSimpleName());
//        Class<?> declaringClass = method.getDeclaringClass();
//        Constructor<?> constructor = declaringClass.getConstructors()[0];
//        Object aggregateObject = constructor.newInstance();

            AggregateClassResolver aggregateClassResolver = new AggregateClassResolver(method);
            Object aggregateObject = aggregateClassResolver.resolve();

            method.invoke(aggregateObject, command);



        } catch (InvocationTargetException | InstantiationException | IllegalAccessException e) {
            throw new RuntimeException(e);
        }
    }
}
