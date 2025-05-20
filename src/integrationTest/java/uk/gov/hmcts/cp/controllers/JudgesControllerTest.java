package uk.gov.hmcts.cp.controllers;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;

@ExtendWith(SpringExtension.class)
@SpringBootTest
@AutoConfigureMockMvc
class JudgesControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @DisplayName("Should /judges/{judge_id} request with 200 response code")
    @Test
    void shouldCallActuatorAndGet200() throws Exception {
        mockMvc.perform(get("/judges/123"))
            .andDo(print())
            .andExpect(status().isOk());
    }
}
