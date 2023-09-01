package zerobase.EasyRemittance.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import zerobase.EasyRemittance.domain.User;
import zerobase.EasyRemittance.dto.Auth;
import zerobase.EasyRemittance.security.TokenProvider;
import zerobase.EasyRemittance.service.UserAuthService;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

public class UserAuthControllerTest {

    private MockMvc mockMvc;

    @Mock
    private UserAuthService userAuthService;

    @Mock
    private TokenProvider tokenProvider;

    @InjectMocks
    private UserAuthController userAuthController;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.initMocks(this);
        mockMvc = MockMvcBuilders.standaloneSetup(userAuthController).build();
    }

    @Test
    public void testSignin() throws Exception {
        // Mock 로그인 요청 객체
        Auth.SignIn signInRequest = new Auth.SignIn();
        signInRequest.setUserId("testUser");
        signInRequest.setPassword("testPassword");

        // Mock 사용자 객체
        User mockUser = new User();
        mockUser.setUserId("testUser");
        mockUser.setPassword("encodedPassword"); // 인코딩된 비밀번호 설정

        // Mock 서비스 메서드 호출 시의 동작 설정
        when(userAuthService.authenticate(signInRequest)).thenReturn(mockUser);
        when(tokenProvider.generateToken(mockUser.getUserId(), mockUser.getRoles())).thenReturn("mockToken");

        // 로그인 API 호출 및 결과 검증
        mockMvc.perform(post("/auth/signin")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(new ObjectMapper().writeValueAsString(signInRequest)))
                .andExpect(status().isOk());
    }

    @Test
    public void testSigninSuccess() throws Exception {
        // 성공하는 로그인 요청 객체
        Auth.SignIn signInRequest = new Auth.SignIn();
        signInRequest.setUserId("testUser");
        signInRequest.setPassword("testPassword");

        // Mock 사용자 객체
        User mockUser = new User();
        mockUser.setUserId("testUser");
        mockUser.setPassword("encodedPassword");

        // Mock 서비스 메서드 호출 시의 동작 설정
        when(userAuthService.authenticate(signInRequest)).thenReturn(mockUser);
        when(tokenProvider.generateToken(mockUser.getUserId(), mockUser.getRoles())).thenReturn("mockToken");

        // 로그인 API 호출 및 성공 결과 검증
        mockMvc.perform(post("/auth/signin")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(new ObjectMapper().writeValueAsString(signInRequest)))
                .andExpect(status().isOk());
    }
}