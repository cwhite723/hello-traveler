package com.hayan.hello_traveler.auth.domain.dto.response;

import com.hayan.hello_traveler.user.domain.constant.Role;

public record UserInfo(String username, Role role) {
}
