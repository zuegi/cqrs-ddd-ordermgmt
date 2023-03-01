package ch.zuegi.ordermgmt.feature.food.shared;

import ch.zuegi.ordermgmt.feature.food.infrastructure.persistence.EventRepository;
import ch.zuegi.ordermgmt.shared.annotation.CommandHandler;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.List;

@Slf4j
@Component
public class CommandGateway {

    private EventRepository eventRepository;

    public CommandGateway(EventRepository eventRepository) {
        this.eventRepository = eventRepository;
    }

    public void send(Object command) {

        // den Wert des @TargetAggregateIdentifer aus dem Command via Reflection auslesen
        // den Wert in einen String umwandeln
         String targetIdentifier; // = hier den Wert abfuellen

        // repository.findByTargeteIdentifer(targetIdentifiert).orElse(Erstelle ein neues Object mit dem TargetIdentifier)
        // irgendwie mit serialization/de-serialization
        // dann habe ich das erstellte @Aggregate

       Object aggregateObject =  eventRepository.findByTargetIdentifier();

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

            AggregateClassResolver aggregateClassResolver = new AggregateClassResolver(method);
//            Object aggregateObject = aggregateClassResolver.resolve();
            // an dieser stellen das aus dem repository erstellte Aggregate Object aufrufen

            method.invoke(aggregateObject, command);

        } catch (InvocationTargetException | IllegalAccessException e) {
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
