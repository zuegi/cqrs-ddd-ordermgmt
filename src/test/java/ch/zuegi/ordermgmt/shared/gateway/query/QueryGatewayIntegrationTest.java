package ch.zuegi.ordermgmt.shared.gateway.query;

import ch.zuegi.ordermgmt.feature.food.domain.command.CreateFoodCartCommand;
import ch.zuegi.ordermgmt.feature.food.domain.query.FindFoodCartQuery;
import ch.zuegi.ordermgmt.feature.food.infrastructure.persistence.FoodCartView;
import ch.zuegi.ordermgmt.shared.gateway.command.CommandGateway;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.UUID;


@SpringBootTest
class QueryGatewayIntegrationTest {

    @Autowired
    QueryGateway queryGateway;

    @Autowired
    CommandGateway commandGateway;

    @Test
    void test() {
        // given an created FoodCart
        UUID foodCartId = UUID.randomUUID();
        System.out.println(foodCartId);
        commandGateway.send(new CreateFoodCartCommand(foodCartId));

        FindFoodCartQuery findFoodCartQuery = new FindFoodCartQuery(foodCartId);
        FoodCartView foodCartView = queryGateway.query(findFoodCartQuery, FoodCartView.class);

        Assertions.assertThat(foodCartView).isNotNull()
                .extracting("id")
                .isEqualTo(foodCartId);
    }

}
