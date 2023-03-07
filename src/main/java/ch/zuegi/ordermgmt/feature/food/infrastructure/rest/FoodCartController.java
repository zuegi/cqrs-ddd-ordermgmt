package ch.zuegi.ordermgmt.feature.food.infrastructure.rest;

import ch.zuegi.ordermgmt.feature.food.domain.command.CreateFoodCartCommand;
import ch.zuegi.ordermgmt.feature.food.domain.command.SelectProductCommand;
import ch.zuegi.ordermgmt.feature.food.shared.CommandGateway;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.UUID;

@RestController
@RequestMapping(value = "/api/foodcart", produces = MediaType.APPLICATION_JSON_VALUE)
public class FoodCartController {

    private final CommandGateway commandGateway;

    public FoodCartController(CommandGateway commandGateway) {
        this.commandGateway = commandGateway;
    }

    @PostMapping("/create")
    public ResponseEntity<UUID> handle() {
        return ResponseEntity.ok(commandGateway.send(new CreateFoodCartCommand(UUID.randomUUID())));
    }

    @PostMapping("/product/add")
    public void handle(SelectProductCommand command) {
        commandGateway.send(command);
    }

}
