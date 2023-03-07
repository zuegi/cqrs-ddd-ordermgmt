package ch.zuegi.ordermgmt.feature.food.infrastructure.rest;

import ch.zuegi.ordermgmt.feature.food.domain.command.CreateFoodCartCommand;
import ch.zuegi.ordermgmt.feature.food.domain.command.SelectProductCommand;
import ch.zuegi.ordermgmt.feature.food.shared.CommandGateway;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "/api/foodcart", produces = MediaType.APPLICATION_JSON_VALUE)
public class FoodCartController {

    private final CommandGateway commandGateway;

    public FoodCartController(CommandGateway commandGateway) {
        this.commandGateway = commandGateway;
    }

    @PostMapping("/create")
    public void handle() {
        commandGateway.send(new CreateFoodCartCommand());
    }

    @PostMapping("/product/add")
    public void handle(SelectProductCommand command) {
        commandGateway.send(command);
    }

}
