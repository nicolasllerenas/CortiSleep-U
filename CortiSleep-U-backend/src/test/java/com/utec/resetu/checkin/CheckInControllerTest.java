package com.utec.resetu.checkin;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.utec.resetu.checkin.application.dto.CheckInRequest;
import com.utec.resetu.checkin.application.service.CheckInService;
import com.utec.resetu.checkin.infrastructure.web.CheckInController;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.context.annotation.Import;
import com.utec.resetu.testsupport.TestSupportConfig;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(value = CheckInController.class, excludeAutoConfiguration = {org.springframework.boot.autoconfigure.orm.jpa.HibernateJpaAutoConfiguration.class, org.springframework.boot.autoconfigure.data.jpa.JpaRepositoriesAutoConfiguration.class, org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration.class})
@Import(TestSupportConfig.class)
@AutoConfigureMockMvc(addFilters = false)
class CheckInControllerTest {

    @org.springframework.context.annotation.Configuration
    @Import(CheckInController.class)
    static class TestConfig {
        // prevents test framework from loading the full ResetUApplication (and @EnableJpaAuditing)
    }

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

    mockMvc.perform(post("/checkins")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isCreated());
    }

    @Test
    void shouldGetMyCheckIns() throws Exception {
    mockMvc.perform(get("/checkins/me"))
                .andExpect(status().isOk());
    }

    @Test
    void shouldGetCheckInStats() throws Exception {
    mockMvc.perform(get("/checkins/me/stats"))
                .andExpect(status().isOk());
    }

    @Test
    void shouldGetTodayCheckIns() throws Exception {
    mockMvc.perform(get("/checkins/me/today"))
                .andExpect(status().isOk());
    }
}
