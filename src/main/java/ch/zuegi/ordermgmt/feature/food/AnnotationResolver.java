package ch.zuegi.ordermgmt.feature.food;

import io.github.classgraph.ClassGraph;
import io.github.classgraph.ScanResult;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

public class AnnotationResolver {

    private static ScanResult scanResult;
    private AnnotationResolver() {
        scanResult = scanResult();
    }

    // soll ein Singletion sein, weil das ScanResult nur einmal ausgeführt werden soll?
    private static AnnotationResolver instance;

    public static AnnotationResolver getInstance() {
        if (instance == null) {
            instance = new AnnotationResolver();
        }
        return instance;
    }


    /*
        Was muss diese Klasse alles anbieten koennen?
     */


    // ein Filter, welche Klassen mit deklarierter Annotation herausfiltert
    public AnnotationResolver filter() {
        // filter und gib die Klasse zurück, damit weitere Methoden aufgelistet werden können
        // muss also so eine Art Builder sein? In einem Singleton?
        // geht das überhaupt
        return this;
    }


    //
    public List<Method> resolveForMethodsWithAnnotation() {
        return null;
    }





    private static ScanResult scanResult() {
        return new ClassGraph()
                .disableJarScanning()
                .enableAllInfo()
                .scan();
    }
}
