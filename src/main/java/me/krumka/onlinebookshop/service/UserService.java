package me.krumka.onlinebookshop.service;

import me.krumka.onlinebookshop.dto.user.UserRegistrationRequestDto;
import me.krumka.onlinebookshop.dto.user.UserResponseDto;
import me.krumka.onlinebookshop.exception.RegistrationException;

public interface UserService {

    UserResponseDto register(UserRegistrationRequestDto userRegistrationRequestDto)
            throws RegistrationException;
}
