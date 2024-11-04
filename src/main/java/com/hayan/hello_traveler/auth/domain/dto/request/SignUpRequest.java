package com.hayan.hello_traveler.auth.domain.dto.request;

import com.hayan.hello_traveler.user.domain.constant.Gender;
import java.time.LocalDate;

public record SignUpRequest(String name,
                            String password,
                            String contact,
                            String username,
                            Gender gender,
                            LocalDate birthday) {

}
