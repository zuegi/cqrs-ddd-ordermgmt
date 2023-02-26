package ch.zuegi.ordermgmt.feature.food;

import io.github.classgraph.*;
import lombok.extern.slf4j.Slf4j;

import java.lang.annotation.Annotation;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;

@Slf4j
public class AggregateMethodResolver {

    private String scanPackage;
    private Class<?> methodAnnotationClass;
    private ScanResult scanResult;

    public AggregateMethodResolver(ScanResult scanResult, Class<?> methodAnnotationClass) {
        this.methodAnnotationClass = methodAnnotationClass;
        this.scanResult = scanResult;
    }

    public List<Method> resolve(Object parameterObject) throws InvocationTargetException, InstantiationException, IllegalAccessException {
        log.info("parameterObject: " + parameterObject.getClass().getName());

        ClassInfoList classesWithAggregateAnnotation = scanResult.getClassesWithAnnotation(Aggregate.class);
        // FIXME Exception wenn die Anzahl nicht stimmt
        assert classesWithAggregateAnnotation.size() > 0;

        // Das ScanResult muss besser unterteilt werden können
        // und es muss in jedes POJO injected werden können
        // und zwar als Singleton

        // also wie mache ich ein Singleton, welches das ScanResult haelt?
        //


        // ich brauche hier einen generischen Annotation Resolver
        return classesWithAggregateAnnotation.stream()
                .map(ClassInfo::getMethodInfo)
                .flatMap(Collection::stream)
                .filter(methodInfo -> methodInfo.hasAnnotation((Class<? extends Annotation>) methodAnnotationClass))
                .map(MethodInfo::loadClassAndGetMethod)
                // FIXME erstelle einen MethodFilter anstelle des Arrays.stream(...)
                // den kann man dann auch testen und ist vielleicht lesbarer
                .filter(m -> Arrays.stream(m.getParameterTypes())
                        .peek(p -> System.out.println("ParameterType: " + p.getSimpleName()))
                        .anyMatch(parameterType -> parameterType.isInstance(parameterObject)))
//                .findAny()

                .toList();
//                .orElseThrow(() -> new NoAnnotatedMethodFoundException(CommandGatewayMessage.NO_WAY));
    }

}
