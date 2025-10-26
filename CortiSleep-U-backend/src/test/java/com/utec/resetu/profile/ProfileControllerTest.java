package com.utec.resetu.profile;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.utec.resetu.profile.application.dto.ProfileRequest;
import com.utec.resetu.profile.application.service.ProfileService;
import com.utec.resetu.profile.infrastructure.web.ProfileController;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(ProfileController.class)
class ProfileControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private ProfileService profileService;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    void shouldGetMyProfile() throws Exception {
        mockMvc.perform(get("/api/v1/profile/me"))
                .andExpect(status().isOk());
    }

    @Test
    void shouldCreateMyProfile() throws Exception {
        ProfileRequest request = ProfileRequest.builder()
                .firstName("Test")
                .lastName("User")
                .email("test@example.com")
                .build();

        mockMvc.perform(post("/api/v1/profile/me")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isCreated());
    }

    @Test
    void shouldUpdateMyProfile() throws Exception {
        ProfileRequest request = ProfileRequest.builder()
                .firstName("Updated")
                .lastName("User")
                .email("updated@example.com")
                .build();

        mockMvc.perform(put("/api/v1/profile/me")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isOk());
    }
}
