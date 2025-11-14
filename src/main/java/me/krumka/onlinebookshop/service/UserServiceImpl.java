package me.krumka.onlinebookshop.service;

import lombok.RequiredArgsConstructor;
import me.krumka.onlinebookshop.dto.user.UserRegistrationRequestDto;
import me.krumka.onlinebookshop.dto.user.UserResponseDto;
import me.krumka.onlinebookshop.exception.RegistrationException;
import me.krumka.onlinebookshop.mapper.UserMapper;
import me.krumka.onlinebookshop.model.User;
import me.krumka.onlinebookshop.repository.user.UserRepository;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;
    private final UserMapper userMapper;

    @Override
    public UserResponseDto register(UserRegistrationRequestDto userRegistrationRequestDto)
            throws RegistrationException {
        if (userRepository.findByEmail(userRegistrationRequestDto.getEmail()).isPresent()) {
            throw new RegistrationException("Cannot register user. Email is already in use.");
        }
        User savedUser = userRepository.save(userMapper.toUser(userRegistrationRequestDto));
        return userMapper.toUserResponseDto(savedUser);
    }
}
