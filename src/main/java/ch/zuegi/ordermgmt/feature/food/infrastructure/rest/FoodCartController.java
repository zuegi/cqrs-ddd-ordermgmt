package ch.zuegi.ordermgmt.feature.food.infrastructure.rest;

import ch.zuegi.ordermgmt.feature.food.domain.command.ConfirmFoodCartCommand;
import ch.zuegi.ordermgmt.feature.food.domain.command.CreateFoodCartCommand;
import ch.zuegi.ordermgmt.feature.food.domain.command.SelectProductCommand;
import ch.zuegi.ordermgmt.feature.food.shared.CommandGateway;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
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
    public ResponseEntity<String> handle() {
        return ResponseEntity.ok(commandGateway.send(new CreateFoodCartCommand(UUID.randomUUID())));
    }

    @PostMapping(value = "/product/add",  consumes = {MediaType.APPLICATION_JSON_VALUE})
    public ResponseEntity<String> handle(@RequestBody SelectProductCommand command) {
        return ResponseEntity.ok(commandGateway.send(command));
    }

    @PostMapping(path = "/confirm")
    public ResponseEntity<String> handle(@RequestBody ConfirmFoodCartCommand command) {
        return ResponseEntity.ok(commandGateway.send(command));
    }
}
