package ch.zuegi.ordermgmt.feature.food.shared;

import ch.zuegi.ordermgmt.feature.food.infrastructure.persistence.EventRepository;
import ch.zuegi.ordermgmt.shared.annotation.CommandHandler;
import ch.zuegi.ordermgmt.shared.annotation.TargetAggregateIdentifier;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.List;
import java.util.UUID;

@Slf4j
@Component
public class CommandGateway {

    private final EventRepository eventRepository;

    public CommandGateway(EventRepository eventRepository) {
        this.eventRepository = eventRepository;
    }

    public UUID send(Object command) {

        // den Wert des @TargetAggregateIdentifer aus dem Command via Reflection auslesen
        UUID targetIdentifier = getTargetIdentifer(command);
        // extrahiere die Methode, welche mit @CommandHandler annotiert ist und command als signature parameter hat
        List<Method> methodList = getMethods(command);

        try {
            assertCorrectNumberOfMethods(methodList);

            Method method = methodList.get(0);

            Object aggregateObject = eventRepository.findByTargetIdentifier(targetIdentifier).orElse( createNewAggregateObject(method));

            // an dieser stellen das aus dem repository erstellte Aggregate Object aufrufen

            method.invoke(aggregateObject, command);

            return targetIdentifier;

        } catch (InvocationTargetException | IllegalAccessException e) {
            // FIXME Exception definieren
            throw new RuntimeException(e);
        }
    }

    private static List<Method> getMethods(Object command) {
        return new AggregatedMethodResolver()
                .filterMethodAnnotatedWith(CommandHandler.class)
                .filterMethodParameter(command)
                .resolve();
    }

    private static UUID getTargetIdentifer(Object command) {
        UUID targetIdentifier = null;
        try {
            Field field = getField(command);
            if (field != null) {
                field.setAccessible(true);
                targetIdentifier = (UUID) field.get(command);
            }
        } catch (IllegalAccessException e) {
            throw new RuntimeException(e);
        }
        return targetIdentifier;
    }

    private static Field getField(Object command) {
        List<Field> fieldList = new AggregatedFieldResolver()
                .filterClasses(command.getClass())
                .filterFieldAnnotationWith(TargetAggregateIdentifier.class)
                .resolve();
        if (fieldList == null || fieldList.isEmpty()) {
            return null;
        }
        Field field = fieldList.get(0);
        assert fieldList.size() == 1;
        return field;
    }

    private static Object createNewAggregateObject(Method method)  {
        try {
            AggregateClassResolver aggregateClassResolver = new AggregateClassResolver(method);
            return aggregateClassResolver.resolve();
        } catch (InvocationTargetException | InstantiationException | IllegalAccessException e) {
            // FIXME Exception definieren
            throw new RuntimeException(e);
        }
    }

    private void assertCorrectNumberOfMethods(List<Method> methodList) {
        if (methodList.size() == 0) {
            throw new NoAnnotatedMethodFoundException(CommandGatewayMessage.NO_WAY);
        }
        if (methodList.size() > 1) {
            throw new ToManyAnnotatedMethodException(CommandGatewayMessage.TO_MANY);
        }
    }
}
