package ch.zuegi.ordermgmt.feature.food.infrastructure.persistence;

import ch.zuegi.ordermgmt.feature.food.shared.AggregatedFieldResolver;
import ch.zuegi.ordermgmt.feature.food.shared.AggregatedMethodResolver;
import ch.zuegi.ordermgmt.shared.annotation.Aggregate;
import ch.zuegi.ordermgmt.shared.annotation.AggregatedEventIdentifier;
import ch.zuegi.ordermgmt.shared.annotation.EventHandler;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.*;

@Slf4j
@Component
public class EventRepository {

    Map<UUID, Map<Class<?>, String>> eventMap;
    ObjectMapper objectMapper;

    public EventRepository() {
        objectMapper = new ObjectMapper();
        eventMap = new LinkedHashMap<>();
    }

    public void on(Object event) {
        // wie finde ich heraus, um welches Event es sich handelt
        // event.getClass(); aber brauche ich vielleicht gar nicht
        // da gibt es Reflection.getCallerClass....
        log.info("hello and welcome unknown object event: {}", event.toString());
        List<Field> fieldList = new AggregatedFieldResolver()
                .filterClasses(event.getClass())
                .filterFieldAnnotationWith(AggregatedEventIdentifier.class)
                .resolve();
        // FIXME fieldlist.size mit Exceptions
        assert fieldList.size() == 1;

        // FIXME kann man schoener machen
        try {
            Field field = fieldList.get(0);
            field.setAccessible(true);
            Object objectId = field.get(event);
            String eventAsString = objectMapper.writeValueAsString(event);
            if (eventMap.containsKey(objectId)) {
                Map<Class<?>, String> classStringMap = eventMap.get(objectId);
                classStringMap.put(event.getClass(), eventAsString);
            } else {
                Map<Class<?>, String> classStringMap = new LinkedHashMap<>();
                classStringMap.put(event.getClass(), eventAsString);
                eventMap.put((UUID) objectId, classStringMap);
            }
        } catch (IllegalAccessException | JsonProcessingException e) {
            throw new RuntimeException(e);
        }

        log.info("eventmap: {}", eventMap.toString());
    }

    public Optional<Object> findByTargetIdentifier(UUID targetIdentifier) {
        // falls einen persistiertes TargetIdentifier gibt
        // Lade die serialisierten Events aus dem Store und de-serialize die Events
        // fahre die Events gegen das Aggregate mit den EventHandler Methoden nach
        // gib das Aggregate zurueck

        if (eventMap.containsKey(targetIdentifier)) {
            Map<Class<?>, String> classStringMap = eventMap.get(targetIdentifier);


            var ref = new Object() {
                Object aggregate = null;
            };
            classStringMap.entrySet()
                    .forEach(classStringEntry -> {
                        log.info("key: {}, value: {}", classStringEntry.getKey(), classStringEntry.getValue());
                        try {
                            Object event = objectMapper.readValue(classStringEntry.getValue(), classStringEntry.getKey());
                            log.info("event name: {}", event.getClass().getSimpleName());

                            List<Method> methodList = new AggregatedMethodResolver()
                                    .filterClassAnnotatedWith(Aggregate.class)
                                    .filterMethodAnnotatedWith(EventHandler.class)
                                    .filterMethodParameter(event)
                                    .resolve();
                            assert methodList.size() == 1;

                            if (ref.aggregate == null) {
                                ref.aggregate = methodList.get(0).getDeclaringClass().newInstance();
                            }

                            methodList.forEach(method -> {
                                try {
                                    method.invoke( ref.aggregate, event);
                                } catch (IllegalAccessException | InvocationTargetException e) {
                                    throw new RuntimeException(e);
                                }
                            });

                        } catch (JsonProcessingException | InstantiationException | IllegalAccessException e) {
                            throw new RuntimeException(e);
                        }

                    });

            return Optional.of(ref.aggregate);
        }

        return Optional.empty();
    }


}

