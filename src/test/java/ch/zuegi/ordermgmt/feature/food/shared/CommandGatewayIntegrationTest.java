package ch.zuegi.ordermgmt.feature.food.shared;

import ch.zuegi.ordermgmt.feature.food.domain.command.CreateFoodCartCommand;
import ch.zuegi.ordermgmt.feature.food.domain.command.SelectProductCommand;
import ch.zuegi.ordermgmt.shared.eventsourcing.EventRepository;
import ch.zuegi.ordermgmt.shared.gateway.command.CommandGateway;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Optional;
import java.util.UUID;


@SpringBootTest
class CommandGatewayIntegrationTest {

    @Autowired
    CommandGateway commandGateway;

    @Autowired
    EventRepository eventRepository;

    @Test
    void assert_uuid_is_the_same() {

        UUID foodCartId = UUID.randomUUID();
        CreateFoodCartCommand createFoodCartCommand = new CreateFoodCartCommand(foodCartId);
        String foodCartIdAsString = commandGateway.send(createFoodCartCommand);
        Assertions.assertThat(foodCartId.toString()).isEqualTo(foodCartIdAsString);
    }

    @Test
    void testemich() {
        UUID foodCartId = UUID.randomUUID();
        CreateFoodCartCommand createFoodCartCommand = new CreateFoodCartCommand(foodCartId);
        String foodCartIdAsString = commandGateway.send(createFoodCartCommand);

        SelectProductCommand selectProductCommand = new SelectProductCommand(foodCartId, UUID.randomUUID(), 1);
        String stillTheSameFoodCartIdAsString = commandGateway.send(selectProductCommand);

        Assertions.assertThat(foodCartId.toString()).isEqualTo(stillTheSameFoodCartIdAsString);


        Optional<Object> byTargetIdentifier = eventRepository.findByTargetIdentifier(foodCartId);
        Assertions.assertThat(byTargetIdentifier).isPresent();

        Assertions.assertThat(byTargetIdentifier.get())
                .isNotNull()
                .extracting("foodCartId")
                .isEqualTo(foodCartId);


    }
}