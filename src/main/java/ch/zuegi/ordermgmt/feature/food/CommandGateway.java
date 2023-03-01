package ch.zuegi.ordermgmt.feature.food;

import ch.zuegi.ordermgmt.shared.annotation.CommandHandler;
import io.github.classgraph.ScanResult;
import org.springframework.stereotype.Component;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.List;

public class CommandGateway {


    public static void send(Object command) {

        List<Method> methodList = new AggregatedMethodResolver()
                .filterMethodAnnotatedWith(CommandHandler.class)
                .filterMethodParameter(command)
                .resolve();

        try {
            // FIXME auslagern in eine Klasse/Methode
            if (methodList.size() == 0) {
                throw new NoAnnotatedMethodFoundException(CommandGatewayMessage.NO_WAY);
            }
            if (methodList.size() > 1) {
                throw new ToManyAnnotatedMethodException(CommandGatewayMessage.TO_MANY);
            }

            Method method = methodList.get(0);

            AggregateClassResolver aggregateClassResolver = new AggregateClassResolver(method);
            Object aggregateObject = aggregateClassResolver.resolve();

            method.invoke(aggregateObject, command);

        } catch (InvocationTargetException | InstantiationException | IllegalAccessException e) {
            // FIXME Exception definieren
            throw new RuntimeException(e);
        }
    }
}
