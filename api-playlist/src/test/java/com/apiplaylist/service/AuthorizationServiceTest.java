package com.apiplaylist.service;

import com.apiplaylist.models.dto.AuthenticationDTO;
import com.apiplaylist.models.dto.LoginResponseDTO;
import com.apiplaylist.models.dto.RegisterDTO;
import com.apiplaylist.models.dto.UserResponse;
import com.apiplaylist.models.entity.User;
import com.apiplaylist.models.enums.UserRole;
import com.apiplaylist.repository.UserRepository;
import com.apiplaylist.security.TokenService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.context.ApplicationContext;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.TestingAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;

import java.util.Collections;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class AuthorizationServiceTest {

    @Mock
    private UserRepository userRepository;

    @Mock
    private TokenService tokenService;

    @Mock
    private AuthenticationManager authenticationManager;

    @Mock
    private ApplicationContext context;

    @InjectMocks
    private AuthorizationService authorizationService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testRegisterUser() {
        RegisterDTO registerDTO = new RegisterDTO("testUser", "testPass", UserRole.USER);

        User user = new User();
        user.setUsername("testUser");
        user.setPassword("encodedPassword");
        user.setUserRole(UserRole.USER);

        Authentication authentication = mock(Authentication.class);
        SecurityContext securityContext = mock(SecurityContext.class);
        when(securityContext.getAuthentication()).thenReturn(authentication);
        SecurityContextHolder.setContext(securityContext);
        when(authentication.getAuthorities()).thenReturn(Collections.emptyList());

        when(userRepository.findByUsername("testUser")).thenReturn(null);
        when(userRepository.save(any(User.class))).thenReturn(user);

        UserResponse userResponse = authorizationService.register(registerDTO);

        assertNotNull(userResponse);
        assertEquals("testUser", userResponse.getUsername());
        assertEquals(UserRole.USER, userResponse.getRole());
    }

    @Test
    void testLogin() {
        var authenticationDTO = new AuthenticationDTO("testUser", "testPass");

        User user = new User();
        user.setUsername("testUser");
        user.setPassword("encodedPassword");

        when(context.getBean(AuthenticationManager.class)).thenReturn(authenticationManager);
        when(authenticationManager.authenticate(any())).thenReturn(new TestingAuthenticationToken(user, null));
        when(tokenService.generateToken(user)).thenReturn("testToken");

        LoginResponseDTO loginResponseDTO = authorizationService.login(authenticationDTO);

        assertNotNull(loginResponseDTO);
        assertEquals("testToken", loginResponseDTO.getToken());
    }

}
