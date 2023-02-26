package ch.zuegi.ordermgmt.feature.food;

import lombok.extern.slf4j.Slf4j;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

@Slf4j
public class AggregateClassResolver {

    private Method method;
    public AggregateClassResolver(Method method) {
        this.method = method;
    }

    public Object resolve() throws InvocationTargetException, InstantiationException, IllegalAccessException {
        log.info("Method name: {}", method.getDeclaringClass().getSimpleName());
        Class<?> declaringClass = method.getDeclaringClass();
        Constructor<?> constructor = declaringClass.getConstructors()[0];
        return constructor.newInstance();
    }
}
