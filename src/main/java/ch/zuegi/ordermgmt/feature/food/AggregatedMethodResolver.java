package ch.zuegi.ordermgmt.feature.food;

import io.github.classgraph.ClassInfo;
import io.github.classgraph.ClassInfoList;
import io.github.classgraph.MethodInfo;
import io.github.classgraph.ScanResult;

import java.lang.annotation.Annotation;
import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;

public class AggregatedMethodResolver {

    private ScanResultCollector scanResultCollector;
    private ScanResult result;
    private ClassInfoList classInfoList;
    private List<MethodInfo> methodInfoList;
    private boolean classFilter;
    private boolean methodFilter;
    private boolean methodParamFilter;
    private Object parameterObject;


    public AggregatedMethodResolver() {
        scanResultCollector = ScanResultCollector.getInstance();
        result = scanResultCollector.getResult();
    }

    /*
        Was muss diese Klasse alles anbieten koennen?
     */


    // ein Filter, welche Klassen mit deklarierter Annotation herausfiltert


    public AggregatedMethodResolver filterClassAnnotatedWith(Class<?> aggregateClass) {
        classInfoList = result.getClassesWithAnnotation((Class<? extends Annotation>) aggregateClass);
        methodInfoList = classInfoList.stream()
                .map(ClassInfo::getMethodInfo)
                .flatMap(Collection::stream)
                .toList();
        return this;
    }

    public AggregatedMethodResolver filterMethodAnnotatedWith(Class<?> aggregatedMethodClass) {
        // filter und gib die Klasse zurück, damit weitere Methoden aufgelistet werden können
        // muss also so eine Art Builder sein? In einem Singleton?
        // geht das überhaupt
        methodInfoList = classInfoList.stream()
                .map(ClassInfo::getMethodInfo)
                .flatMap(Collection::stream)
                .filter(methodInfo -> methodInfo.hasAnnotation((Class<? extends Annotation>) aggregatedMethodClass))
                .toList();

        return this;
    }

    public AggregatedMethodResolver filterMethodParameter(Object parameterObject) {
        this.parameterObject = parameterObject;
        methodParamFilter = true;
        return this;
    }


    //
    public List<Method> resolveExactlyOne() {
        return null;
    }

    public List<Method> resolve() {
        if ( methodParamFilter) {
            return methodInfoList.stream()
                    .map(MethodInfo::loadClassAndGetMethod)
                    // FIXME erstelle einen MethodFilter anstelle des Arrays.stream(...)
                    // den kann man dann auch testen und ist vielleicht lesbarer
                    .filter(m -> Arrays.stream(m.getParameterTypes())
                            .peek(p -> System.out.println("ParameterType: " + p.getSimpleName()))
                            .anyMatch(parameterType -> parameterType.isInstance(parameterObject)))
                    .toList();
        }
        // else
        return methodInfoList.stream()
                .map(MethodInfo::loadClassAndGetMethod)
                .toList();
    }


}
