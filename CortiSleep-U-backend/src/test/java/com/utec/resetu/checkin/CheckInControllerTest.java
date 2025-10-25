package com.utec.resetu.checkin;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.utec.resetu.checkin.application.dto.CheckInRequest;
import com.utec.resetu.checkin.application.service.CheckInService;
import com.utec.resetu.checkin.infrastructure.web.CheckInController;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(CheckInController.class)
class CheckInControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private CheckInService checkInService;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    void shouldCreateCheckIn() throws Exception {
        CheckInRequest request = CheckInRequest.builder()
                .locationName("Test Location")
                .moodScore(8)
                .stressLevel(3)
                .energyLevel(7)
                .notes("Test check-in")
                .build();

        mockMvc.perform(post("/api/v1/checkin")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isCreated());
    }

    @Test
    void shouldGetMyCheckIns() throws Exception {
        mockMvc.perform(get("/api/v1/checkin/my"))
                .andExpect(status().isOk());
    }

    @Test
    void shouldGetCheckInStats() throws Exception {
        mockMvc.perform(get("/api/v1/checkin/stats"))
                .andExpect(status().isOk());
    }

    @Test
    void shouldGetTodayCheckIns() throws Exception {
        mockMvc.perform(get("/api/v1/checkin/today"))
                .andExpect(status().isOk());
    }
}
