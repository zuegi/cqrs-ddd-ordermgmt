package ch.zuegi.ordermgmt.feature.ticket.infrastructure.rest;

import ch.zuegi.ordermgmt.feature.ticket.domain.command.CreateTicketCommand;
import ch.zuegi.ordermgmt.shared.eventsourcing.EventRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.UUID;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
class TicketCommandControllerIntegrationTest {
    @Autowired
    private MockMvc mockMvc;

    @Autowired
    EventRepository eventRepository;

    // hier weiterfahren
    @Test
    void create_ticket_valid() throws Exception {
        // given
        UUID ticketId = UUID.randomUUID();

        CreateTicketCommand createTicketCommand = new CreateTicketCommand(ticketId );
        // when
        String contentAsString = this.mockMvc.perform(
                        post("/api/ticket/create")
                                .contentType(MediaType.APPLICATION_JSON_VALUE)
                                .content(asJsonString(createTicketCommand)))
                .andExpect(status().isOk())
                .andReturn()
                .getResponse()
                .getContentAsString();

        // then
        Assertions.assertThat(contentAsString).isNotEmpty();

        Assertions.assertThat(UUID.fromString(contentAsString))
                .isEqualTo(ticketId);
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
