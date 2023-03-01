package ch.zuegi.ordermgmt.feature.food.shared;


import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.List;

public class AggregateLifeCycle {

    // ich glaube, diese Klasse ist falsch implementiert,
    // die muss nicht das Aggregate aufrufen, sondern muss den
    // Event irgendwie speichen
    // d.h. sie muss eine SpringComponent finden, welche eine Annotation,
    // sagen wir mal EventSourcing sucht und dann damit den Event speicher

    public static void apply(Object event) {

        List<Method> methodList = new AggregatedMethodResolver()
                .filterMethodAnnotatedWith(EventSourcing.class)
                .filterMethodParameter(event)
                .resolve();

            methodList.forEach(method -> {
                try {
                    AggregateClassResolver aggregateClassResolver = new AggregateClassResolver(method);
                    Object aggregateObject = aggregateClassResolver.resolve();

                    method.invoke(aggregateObject, event);

                } catch (InvocationTargetException | InstantiationException | IllegalAccessException e) {
                    // FIXME Exception definieren
                    throw new RuntimeException(e);
                }
            });

    }

}
