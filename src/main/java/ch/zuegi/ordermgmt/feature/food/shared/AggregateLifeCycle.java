package ch.zuegi.ordermgmt.feature.food.shared;


import ch.zuegi.ordermgmt.shared.annotation.EventSourcing;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.List;

public class AggregateLifeCycle {

    public static void apply(Object event) {

        List<Method> methodList = new AggregatedMethodResolver()
                .filterMethodAnnotatedWith(EventSourcing.class)
                .filterMethodParameter(event)
                .resolve();

            methodList.forEach(method -> {
                try {

                    AggregateClassResolver aggregateClassResolver = new AggregateClassResolver(method);
                    Object aggregateObject = aggregateClassResolver
                            .resolve();


                    method.invoke(aggregateObject, event);

                } catch (InvocationTargetException | InstantiationException | IllegalAccessException e) {
                    // FIXME Exception definieren
                    throw new RuntimeException(e);
                }
            });

    }

}
