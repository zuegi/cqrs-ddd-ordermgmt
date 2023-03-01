package ch.zuegi.ordermgmt.feature.food;

import ch.zuegi.ordermgmt.feature.food.domain.FoodCart;
import ch.zuegi.ordermgmt.feature.food.domain.command.CreateFoodCartCommand;
import ch.zuegi.ordermgmt.shared.annotation.CommandHandler;
import io.github.classgraph.*;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;

class FoodCartUnitTest {

    @Test
    void test() throws InvocationTargetException, IllegalAccessException {
        CreateFoodCartCommand command = new CreateFoodCartCommand();
        Assertions.assertThat(command).isNotNull();

        ScanResult scanResult = new ClassGraph()
                .acceptPackages("ch.zuegi.ordermgmt")
//                .verbose()
                .disableJarScanning()
                .enableAllInfo()
                .scan();

        ClassInfo classInfo1 = scanResult.getClassInfo(FoodCart.class.getName());
        MethodInfoList methodInfoList = classInfo1.getMethodInfo();
        for (MethodInfo methodInfo : methodInfoList) {
            String name = methodInfo.getName();
            if (methodInfo.hasAnnotation(CommandHandler.class)) {
                MethodParameterInfo[] parameterInfoArray = methodInfo.getParameterInfo();
                for (MethodParameterInfo methodParameterInfo : parameterInfoArray) {
                    String parameterName = methodParameterInfo.getTypeDescriptor().toStringWithSimpleNames();
                    System.out.println("parameter: " +parameterName);
                }
            }

        }


        // Classes with MethodAnnotation CommandHandle
        ClassInfoList commandHandlerClassInfoList = scanResult.getClassesWithMethodAnnotation(CommandHandler.class.getName());
        Assertions.assertThat(commandHandlerClassInfoList).hasSize(2);


        List<Method> commandHandlerMethods = Objects.requireNonNull(commandHandlerClassInfoList.stream()
                        .filter(classInfo -> classInfo.hasAnnotation(Aggregate.class))
                        .map(ClassInfo::loadClass)
                        .peek(aClass -> {
                            System.out.println("Class: " + aClass.getName());
                        })
                        .map(aClass -> Arrays.stream(aClass.getDeclaredMethods())
                                .filter(m -> m.isAnnotationPresent(CommandHandler.class))
                                .peek(m -> System.out.println("CommandHandlerMethod: " + m.getName()))
                                // filter for params in method signature
                                .filter(m -> Arrays.stream(m.getParameterTypes())
                                        .peek(p -> System.out.println("ParameterType: " + p.getSimpleName()))
                                        .anyMatch(parameterType -> parameterType.isInstance(command))))
                        .findAny()
                        .orElseThrow(() -> new IllegalArgumentException("hallo")))
                .toList();



        Assertions.assertThat(commandHandlerMethods).hasSize(1);

        List<?> objects = commandHandlerClassInfoList.stream()
                .filter(classInfo -> classInfo.hasAnnotation(Aggregate.class))
                .map(ClassInfo::loadClass)
                .map(aClass -> aClass.getConstructors())
                .flatMap(constructors -> Arrays.stream(constructors).map(constructor -> {
                    try {
                        return constructor.newInstance();
                    } catch (InstantiationException e) {
                        throw new RuntimeException(e);
                    } catch (IllegalAccessException e) {
                        throw new RuntimeException(e);
                    } catch (InvocationTargetException e) {
                        throw new RuntimeException(e);
                    }
                }))
                .toList();

        Object o1 = objects.get(0);
        commandHandlerMethods.get(0).invoke(o1, command);

    }

}
