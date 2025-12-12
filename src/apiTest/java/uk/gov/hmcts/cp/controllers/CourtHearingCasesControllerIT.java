package uk.gov.hmcts.cp.controllers;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import static java.util.UUID.randomUUID;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(SpringExtension.class)
@SpringBootTest
class CourtHearingCasesControllerIT {

    @Autowired
    private WebApplicationContext context;

    private MockMvc mockMvc;

    @BeforeEach
    void setUp() {
        mockMvc = MockMvcBuilders.webAppContextSetup(context).build();
    }

    @Test
    void shouldReturnOkWhenValidCaseIdIsProvided() throws Exception {
        String caseId = randomUUID().toString();
        mockMvc.perform(get("/cases/{case_id}/results", caseId)
                            .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isOk())
            .andExpect(result -> {
                String responseBody = result.getResponse().getContentAsString();
                JsonNode jsonBody = new ObjectMapper().readTree(result.getResponse().getContentAsString());

                assertEquals("caseResults", jsonBody.fieldNames().next());
                JsonNode courtResults = jsonBody.get("caseResults");
                assertTrue(courtResults.isArray());
                assertEquals(1, courtResults.size());

                String resultText = courtResults.get(0).get("resultText").asText();
                assertEquals("This is the example outcome of case results", resultText);
            });
    }

    @DisplayName("Should /cases/{case_id}/results request with 200 response code")
    @Test
    void shouldCallActuatorAndGet200() throws Exception {
        mockMvc.perform(get("/cases/123/results"))
            .andDo(print())
            .andExpect(status().isOk());
    }
}
