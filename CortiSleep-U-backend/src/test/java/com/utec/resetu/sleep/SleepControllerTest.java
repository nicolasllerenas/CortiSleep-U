package com.utec.resetu.sleep;

import com.utec.resetu.sleep.application.dto.SleepEntryResponse;
import com.utec.resetu.sleep.infrastructure.web.SleepController;
import com.utec.resetu.sleep.application.service.SleepService;
import com.utec.resetu.shared.security.CurrentUserService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.autoconfigure.orm.jpa.HibernateJpaAutoConfiguration;
import org.springframework.boot.autoconfigure.data.jpa.JpaRepositoriesAutoConfiguration;
import org.springframework.boot.test.mock.mockito.MockBean;
import com.utec.resetu.auth.application.service.JwtService;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(value = SleepController.class, excludeAutoConfiguration = {HibernateJpaAutoConfiguration.class, JpaRepositoriesAutoConfiguration.class})
@AutoConfigureMockMvc(addFilters = false)
public class SleepControllerTest {

    @Configuration
    @Import(SleepController.class)
    static class TestConfig {
        // prevents test framework from loading the full ResetUApplication (and @EnableJpaAuditing)
    }

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private SleepService sleepService;

    @MockBean
    private CurrentUserService currentUserService;

    @MockBean
    private JwtService jwtService;

    @Test
    public void createSleepEntry_ReturnsCreated() throws Exception {
        when(currentUserService.getCurrentUserId()).thenReturn(1L);

        SleepEntryResponse resp = SleepEntryResponse.builder()
                .id(1L)
                .userId(1L)
                .sleepAt("2025-11-11T23:30:00Z")
                .wakeAt("2025-11-12T07:30:00Z")
                .durationMinutes(480)
                .createdAt("2025-11-12T07:30:00Z")
                .build();

        when(sleepService.createSleepEntry(eq(1L), any())).thenReturn(resp);

        String payload = "{\"sleepAt\":\"2025-11-11T23:30:00Z\",\"wakeAt\":\"2025-11-12T07:30:00Z\"}";

    mockMvc.perform(post("/sleep").with(csrf()).contentType(MediaType.APPLICATION_JSON).content(payload))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.data.id").value(1));
    }
}
