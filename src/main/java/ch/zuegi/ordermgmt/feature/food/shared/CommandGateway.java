package ch.zuegi.ordermgmt.feature.food.shared;

import ch.zuegi.ordermgmt.feature.food.infrastructure.persistence.EventRepository;
import ch.zuegi.ordermgmt.shared.annotation.CommandHandler;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Slf4j
@Component
public class CommandGateway {

    private final EventRepository eventRepository;

    public CommandGateway(EventRepository eventRepository) {
        this.eventRepository = eventRepository;
    }

    public void send(Object command) {

        // den Wert des @TargetAggregateIdentifer aus dem Command via Reflection auslesen
        // den Wert in einen String umwandeln
        UUID targetIdentifier = null;
        try {
            Optional<Field> optionalField = getField(command);
            if (optionalField.isPresent()) {
                targetIdentifier = (UUID) optionalField.get().get(command);
            }
        } catch (IllegalAccessException e) {
            throw new RuntimeException(e);
        }

        // repository.findByTargeteIdentifer(targetIdentifiert).orElse(Erstelle ein neues Object mit dem TargetIdentifier)
        // irgendwie mit serialization/de-serialization
        // dann habe ich das erstellte @Aggregate


        // dann koennte ich alle Aktionen auf dem Aggregate ausfuehren
        // method.invoke(@Aggregate, command)
        // ware vielleicht effizienter als die folgenden Zeieln
        List<Method> methodList = new AggregatedMethodResolver()
                .filterMethodAnnotatedWith(CommandHandler.class)
                .filterMethodParameter(command)
                .resolve();

        try {
            assertCorrectNumberOfMethods(methodList);

            Method method = methodList.get(0);

            Object aggregateObject = eventRepository.findByTargetIdentifier(targetIdentifier).orElse( createNewAggregateObject(method));

            // an dieser stellen das aus dem repository erstellte Aggregate Object aufrufen

            method.invoke(aggregateObject, command);

        } catch (InvocationTargetException | IllegalAccessException e) {
            // FIXME Exception definieren
            throw new RuntimeException(e);
        }
    }

    private static Optional<Field> getField(Object command) {
        List<Field> fieldList = new AggregatedFieldResolver()
                .filterClasses(command.getClass())
                .resolve();
        if (fieldList == null || fieldList.isEmpty()) {
            return Optional.empty();
        }
        Field field = fieldList.get(0);
        assert fieldList.size() == 1;
        return Optional.of(field);
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
