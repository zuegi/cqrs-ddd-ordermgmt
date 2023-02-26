package ch.zuegi.ordermgmt.feature.food;

import io.github.classgraph.*;

import java.lang.annotation.Annotation;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.Collection;

public class AggregateMethodResolver {

    private String scanPackage;
    private Class<?> methodAnnotationClass;
    private ScanResult scanResult;

    public AggregateMethodResolver(ScanResult scanResult, Class<?> methodAnnotationClass) {
        this.methodAnnotationClass = methodAnnotationClass;
        this.scanResult = scanResult;
    }

    public Method resolve(Object command) throws InvocationTargetException, InstantiationException, IllegalAccessException {
        System.out.println("command: " + command.getClass().getName());
        // hmmm fuer was auch immer <-- warum habe ich diesen kommentar geschrieben?
        ClassInfoList classesWithAggregateAnnotation = scanResult.getClassesWithAnnotation(Aggregate.class);
        // FIXME Exception wenn die Anzahl nicht stimmt
        assert classesWithAggregateAnnotation.size() > 0;


        return classesWithAggregateAnnotation.stream()
                .map(ClassInfo::getMethodInfo)
                .flatMap(Collection::stream)
                .filter(methodInfo -> methodInfo.hasAnnotation((Class<? extends Annotation>) methodAnnotationClass))
                .map(MethodInfo::loadClassAndGetMethod)
                // FIXME erstelle einen MethodFilter anstelle des Arrays.stream(...)
                // den kann man dann auch testen und ist vielleicht lesbarer
                .filter(m -> Arrays.stream(m.getParameterTypes())
                        .peek(p -> System.out.println("ParameterType: " + p.getSimpleName()))
                        .anyMatch(parameterType -> parameterType.isInstance(command)))
                .findAny()
                .orElseThrow(() -> new NoAnnotatedMethodFoundException(CommandGatewayMessage.NO_WAY));
    }

}
