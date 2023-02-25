package ch.zuegi.ordermgmt.feature.food;

import ch.zuegi.ordermgmt.shared.annotation.CommandHandler;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

class AggregateMethodResolverUnitTest {

    @Test
    void create_food_cart_with_valid_command() throws InvocationTargetException, IllegalAccessException, InstantiationException {
        // given
        CreateFoodCartCommand command = new CreateFoodCartCommand();
        AggregateMethodResolver aggregateMethodResolver = new AggregateMethodResolver( CommandHandler.class);
        // when
        Method method = aggregateMethodResolver.resolve(command);
        // then
        Assertions.assertThat(method).isNotNull()
                .extracting(Method::getName, Method::getDeclaringClass)
                .containsExactly("handle", FoodCart.class);

    }

    @Test
    void create_food_cart_with_invalid_command() throws InvocationTargetException, InstantiationException, IllegalAccessException {
        // given
        InvalidCreateFoodCartCommand command =  new InvalidCreateFoodCartCommand();
        AggregateMethodResolver aggregateMethodResolver = new AggregateMethodResolver(CommandHandler.class);
        // when
        Assertions.assertThatExceptionOfType(NoAnnotatedMethodFoundException.class)
                .isThrownBy(() -> aggregateMethodResolver.resolve(command))
                .withMessage(CommandGatewayMessage.NO_WAY);
        // then
//        Object resolve = AggregateMethodResolver.resolve(command);
    }

    private record InvalidCreateFoodCartCommand() {
    }
}
