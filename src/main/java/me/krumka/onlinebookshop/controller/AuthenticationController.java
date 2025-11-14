package me.krumka.onlinebookshop.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import me.krumka.onlinebookshop.dto.user.UserRegistrationRequestDto;
import me.krumka.onlinebookshop.dto.user.UserResponseDto;
import me.krumka.onlinebookshop.exception.RegistrationException;
import me.krumka.onlinebookshop.service.UserService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RestController
@RequestMapping("/auth")
@Tag(name = "Authentication", description = "Endpoints for user login")
public class AuthenticationController {
    private final UserService userService;

    @PostMapping("/registration")
    @Operation(
            summary = "Register user",
            description = "Registers user and returns registered user info"
    )
    public UserResponseDto register(@RequestBody @Valid UserRegistrationRequestDto requestDto)
            throws RegistrationException {
        return userService.register(requestDto);
    }
}
