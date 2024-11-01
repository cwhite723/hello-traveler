package com.hayan.hello_traveler.auth.controller;

import static com.hayan.hello_traveler.common.response.SuccessCode.SUCCESS;

import com.hayan.hello_traveler.auth.domain.dto.request.SignInRequest;
import com.hayan.hello_traveler.auth.domain.dto.request.SignUpRequest;
import com.hayan.hello_traveler.auth.domain.dto.response.SignInResponse;
import com.hayan.hello_traveler.auth.service.AuthService;
import com.hayan.hello_traveler.common.response.ApplicationResponse;
import com.hayan.hello_traveler.common.response.SuccessCode;
import com.hayan.hello_traveler.user.service.UserService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class AuthController {

  private final UserService userService;
  private final AuthService authService;

  @PostMapping("/sign-up/{user-type}")
  public ApplicationResponse<Void> signUp(@PathVariable("user-type") String type,
      @RequestBody SignUpRequest signUpRequest) {
    userService.save(type, signUpRequest);
    return ApplicationResponse.noData(SUCCESS);
  }

  @PostMapping("/sign-in")
  public ApplicationResponse<SignInResponse> signIn(@RequestBody SignInRequest signInRequest) {
    SignInResponse signInResponse = authService.getUserInfo(signInRequest);
    return ApplicationResponse.ok(signInResponse, SUCCESS);
  }

  @PostMapping("/refresh-token")
  public ApplicationResponse<Void> refreshAccessToken(HttpServletRequest request,
      HttpServletResponse response) {
    authService.refreshAccessToken(request, response);
    return ApplicationResponse.noData(SuccessCode.SUCCESS);
  }
}