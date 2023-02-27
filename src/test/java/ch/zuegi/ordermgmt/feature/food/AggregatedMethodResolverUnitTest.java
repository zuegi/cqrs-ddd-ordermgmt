package ch.zuegi.ordermgmt.feature.food;

import ch.zuegi.ordermgmt.shared.annotation.CommandHandler;
import ch.zuegi.ordermgmt.shared.annotation.EventHandler;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;

import java.lang.reflect.Method;
import java.util.List;

class AggregatedMethodResolverUnitTest {


    @Test
    void filter_for_all_method_with_class_annotation() {
        // given
        AggregatedMethodResolver aggregatedMethodResolver = new AggregatedMethodResolver();
        // when
        List<Method> methodList = aggregatedMethodResolver.filterClassAnnotatedWith(Pillepalle.class)
                .resolve();
        // then
        Assertions.assertThat(methodList).hasSize(4);
    }

    @Test
    void filter_for_all_annotated_method_with_class_annotation() {
        // given
        AggregatedMethodResolver aggregatedMethodResolver = new AggregatedMethodResolver();
        // when
        List<Method> methodList = aggregatedMethodResolver.filterClassAnnotatedWith(Pillepalle.class)
                .filterMethodAnnotatedWith(CommandHandler.class)
                .resolve();
        // then
        Assertions.assertThat(methodList).hasSize(2);
    }

    @Test
    void filter_for_all_annotated_method_with_param_with_class_annotation() {
        // given
        CreateFoodCartCommand command = new CreateFoodCartCommand();
        AggregatedMethodResolver aggregatedMethodResolver = new AggregatedMethodResolver();
        // when
        List<Method> methodList = aggregatedMethodResolver.filterClassAnnotatedWith(Pillepalle.class)
                .filterMethodAnnotatedWith(CommandHandler.class)
                .filterMethodParameter(command)
                .resolve();
        // then
        Assertions.assertThat(methodList).hasSize(1);
    }


    /*
        Inner TestClass
     */
    @Pillepalle
    public static class TestPillepalle {

        public static void method1() {
        }

        @CommandHandler
        public static void createFoodCartCommand(CreateFoodCartCommand commmand) {
        }

        @CommandHandler
        public static void addSelectedProduct(AddSelectedProduct commmand) {
        }
        @EventHandler
        public static void methodWithEvent(FoodCartCreatedEvent event) {
        }

    }


    // Inner Annotation for testing purpose
    public static @interface Pillepalle {
    }

    private static record AddSelectedProduct() {
    }
}

