package ch.zuegi.ordermgmt.feature.food.infrastructure.rest;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
class FoodCartControllerIntegrationTest {
    @Autowired
    private MockMvc mockMvc;

    @Test
    void testemich() throws Exception {
        this.mockMvc.perform(post("/api/foodcart/create"))/*.andDo(print())*/.andExpect(status().isOk());
    }
}
