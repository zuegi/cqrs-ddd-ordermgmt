package ch.zuegi.ordermgmt.feature.food.infrastructure.rest;

import ch.zuegi.ordermgmt.feature.food.domain.FoodCart;
import ch.zuegi.ordermgmt.feature.food.domain.command.SelectProductCommand;
import ch.zuegi.ordermgmt.feature.food.infrastructure.persistence.EventRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Optional;
import java.util.UUID;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
class FoodCartControllerIntegrationTest {
    @Autowired
    private MockMvc mockMvc;

    @Autowired
    EventRepository eventRepository;


    @Test
    void create_foodcart_and_add_selected_product_valid() throws Exception {
        // given
        UUID uuid = createFoodCart();

        UUID selectProductUuid = UUID.randomUUID();
        SelectProductCommand selectProductCommand = new SelectProductCommand(uuid, selectProductUuid, 1);
        // when
        this.mockMvc.perform(
                post("/api/foodcart/product/add")
                        .contentType(MediaType.APPLICATION_JSON_VALUE)
                        .content(asJsonString(selectProductCommand)))
                .andExpect(status().isOk());

        // then
        Optional<Object> byTargetIdentifier = eventRepository.findByTargetIdentifier(uuid);
        FoodCart foodCart = (FoodCart) byTargetIdentifier.get();

        Assertions.assertThat(foodCart).isNotNull()
                .extracting(FoodCart::getFoodCartId, FoodCart::isConfirmed)
                .contains(uuid, false);
    }

    @Test
    void create_foodcart_valid() throws Exception {
        createFoodCart();
    }

    private UUID createFoodCart() throws Exception {
        String contentAsString = this.mockMvc.perform(post("/api/foodcart/create"))
                /*.andDo(print())*/
                .andExpect(status().isOk())
                .andReturn()
                .getResponse()
                .getContentAsString();

        Assertions.assertThat(contentAsString).isNotEmpty();

        UUID uuid = UUID.fromString(contentAsString);
        System.out.println("CreateFoodCart.uuid: " +uuid.toString());
        return uuid;
    }

    private static String asJsonString(final Object obj) {
        try {
            final ObjectMapper mapper = new ObjectMapper();
            return mapper.writeValueAsString(obj);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
