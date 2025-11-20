package com.utec.resetu.auth;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.utec.resetu.auth.application.dto.LoginRequest;
import com.utec.resetu.auth.application.dto.RegisterRequest;
import com.utec.resetu.auth.application.service.AuthService;
import com.utec.resetu.auth.infrastructure.web.AuthController;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.context.annotation.Import;
import com.utec.resetu.testsupport.TestSupportConfig;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(value = AuthController.class, excludeAutoConfiguration = {org.springframework.boot.autoconfigure.orm.jpa.HibernateJpaAutoConfiguration.class, org.springframework.boot.autoconfigure.data.jpa.JpaRepositoriesAutoConfiguration.class, org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration.class})
@Import(TestSupportConfig.class)
@AutoConfigureMockMvc(addFilters = false)
class AuthControllerTest {

        @org.springframework.context.annotation.Configuration
        @Import(AuthController.class)
        static class TestConfig {
                // prevents test framework from loading the full ResetUApplication (and @EnableJpaAuditing)
        }

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private AuthService authService;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    void shouldRegisterUser() throws Exception {
        RegisterRequest request = RegisterRequest.builder()
                .email("test@example.com")
                .password("password123")
                .firstName("Test")
                .lastName("User")
                .build();

        mockMvc.perform(post("/auth/register")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isCreated());
    }

    @Test
    void shouldLoginUser() throws Exception {
        LoginRequest request = LoginRequest.builder()
                .email("test@example.com")
                .password("password123")
                .build();

        mockMvc.perform(post("/auth/login")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isOk());
    }
}
