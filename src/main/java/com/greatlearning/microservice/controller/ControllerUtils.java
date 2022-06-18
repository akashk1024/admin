package com.greatlearning.microservice.controller;

import com.greatlearning.microservice.dto.UserDto;

public class ControllerUtils {

    public static UserDto buildUserDto(String userName, String password, boolean enabled, String authority) {
        UserDto userDto= UserDto.builder()
                .username(userName)
                .password(password)
                .enabled(enabled)
                .authority(authority).build();
        return userDto;
    }

    public static UserDto buildUserDto(String userName, String password, boolean enabled) {
        UserDto userDto= UserDto.builder()
                .username(userName)
                .password(password)
                .enabled(enabled)
                .authority("ROLE_USER").build();
        return userDto;
    }
}
