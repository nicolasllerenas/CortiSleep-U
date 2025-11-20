package com.utec.resetu.testsupport;

import com.utec.resetu.auth.application.service.JwtService;
import com.utec.resetu.shared.security.CurrentUserService;
import org.mockito.Mockito;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;

@TestConfiguration
public class TestSupportConfig {

    @Bean
    public CurrentUserService currentUserService() {
        // provide a Mockito mock for CurrentUserService and default the id to 1L
        CurrentUserService mock = Mockito.mock(CurrentUserService.class);
        Mockito.when(mock.getCurrentUserId()).thenReturn(1L);
        return mock;
    }

    @Bean
    public JwtService jwtService() {
        // provide a Mockito mock for JwtService so security filters can be initialized safely in tests
        return Mockito.mock(JwtService.class);
    }
}
