package ch.zuegi.ordermgmt.feature.food.infrastructure.rest;

import ch.zuegi.ordermgmt.feature.food.shared.CommandGateway;
import ch.zuegi.ordermgmt.feature.food.domain.command.CreateFoodCartCommand;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "/api/foodcart", produces = MediaType.APPLICATION_JSON_VALUE)
public class FoodCartController {


    @PostMapping("/create")
    public void handle() {
        CommandGateway.send(new CreateFoodCartCommand());
    }

}
