package com.utec.resetu.profile;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.utec.resetu.profile.application.dto.ProfileRequest;
import com.utec.resetu.profile.application.service.ProfileService;
import com.utec.resetu.profile.infrastructure.web.ProfileController;
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

@WebMvcTest(value = ProfileController.class, excludeAutoConfiguration = {org.springframework.boot.autoconfigure.orm.jpa.HibernateJpaAutoConfiguration.class, org.springframework.boot.autoconfigure.data.jpa.JpaRepositoriesAutoConfiguration.class, org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration.class})
@Import(TestSupportConfig.class)
@AutoConfigureMockMvc(addFilters = false)
class ProfileControllerTest {

        @org.springframework.context.annotation.Configuration
        @Import(ProfileController.class)
        static class TestConfig {
                // prevents test framework from loading the full ResetUApplication (and @EnableJpaAuditing)
        }

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private ProfileService profileService;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    void shouldGetMyProfile() throws Exception {
        mockMvc.perform(get("/profiles/me"))
                .andExpect(status().isOk());
    }

    @Test
    void shouldCreateMyProfile() throws Exception {
        ProfileRequest request = ProfileRequest.builder()
                .alias("TestUser")
                .faculty("CIENCIA_COMPUTACION")
                .career("Ciencia de la Computación")
                .build();

        mockMvc.perform(post("/profiles")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isCreated());
    }

    @Test
    void shouldUpdateMyProfile() throws Exception {
        ProfileRequest request = ProfileRequest.builder()
                .alias("UpdatedUser")
                .faculty("CIENCIA_COMPUTACION")
                .career("Ciencia de la Computación")
                .build();

        mockMvc.perform(put("/profiles/me")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isOk());
    }
}
