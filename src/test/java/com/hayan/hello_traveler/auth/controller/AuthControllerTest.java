package com.hayan.hello_traveler.auth.controller;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.hayan.hello_traveler.user.domain.Host;
import com.hayan.hello_traveler.user.domain.constant.Gender;
import com.hayan.hello_traveler.user.domain.constant.Role;
import com.hayan.hello_traveler.user.repository.UserRepository;
import java.time.LocalDate;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

@SpringBootTest
@ActiveProfiles("test")
@AutoConfigureMockMvc
@Transactional
public class AuthControllerTest {

  @Autowired
  private MockMvc mockMvc;

  @Autowired
  private UserRepository userRepository;

  @Autowired
  private BCryptPasswordEncoder passwordEncoder;

  @BeforeEach
  public void setUP() {
    Host host = new Host("조하얀",
        passwordEncoder.encode("비번123!"),
        "010-1111-2222",
        "하야닝",
        Gender.FEMALE,
        LocalDate.of(1997, 3, 27), Role.USER);
    userRepository.save(host);
  }

  @Test
  public void 회원가입_성공() throws Exception {
    String signUpRequestJson = """
            {
                "name": "김땡땡",
                "password": "비번123!",
                "contact": "010-2222-3333",
                "username": "동그랑땡",
                "gender": "MALE",
                "birthday": "1998/05/12"
            }
        """;

    mockMvc.perform(post("/api/auth/sign-up/host")
            .contentType(MediaType.APPLICATION_JSON)
            .content(signUpRequestJson))
        .andExpect(status().isOk())
        .andExpect(jsonPath("$.responseCode").value("00"))
        .andExpect(jsonPath("$.responseMessage").value("success"));
  }

  @Test
  public void 아이디와_비밀번호가_맞으면_로그인에_성공한다() throws Exception {
    String signInRequestJson = """
            {
                "username": "하야닝",
                "password": "비번123!"
            }
        """;

    mockMvc.perform(post("/api/auth/sign-in")
            .contentType(MediaType.APPLICATION_JSON)
            .content(signInRequestJson))
        .andExpect(status().isOk());
  }
}