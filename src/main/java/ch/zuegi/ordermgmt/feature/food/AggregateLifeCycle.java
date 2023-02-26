package ch.zuegi.ordermgmt.feature.food;


import ch.zuegi.ordermgmt.shared.annotation.EventHandler;
import io.github.classgraph.ClassGraph;
import io.github.classgraph.ScanResult;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.List;

public class AggregateLifeCycle {
    public static void apply(Object event) {
        AggregateMethodResolver aggregateMethodResolver = new AggregateMethodResolver(scanResult(), EventHandler.class);

        try {
            List<Method> methodList = aggregateMethodResolver.resolve(event);

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
        } catch (InvocationTargetException | InstantiationException | IllegalAccessException e) {
            // FIXME Exception definieren
            throw new RuntimeException(e);
        }

    }


    private static ScanResult scanResult() {
        return new ClassGraph()
                .disableJarScanning()
                .enableAllInfo()
                .scan();
    }
}
