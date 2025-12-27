package me.krumka.onlinebookshop.service;

import jakarta.transaction.Transactional;
import java.util.Set;
import lombok.RequiredArgsConstructor;
import me.krumka.onlinebookshop.dto.user.UserRegistrationRequestDto;
import me.krumka.onlinebookshop.dto.user.UserResponseDto;
import me.krumka.onlinebookshop.exception.EntityNotFoundException;
import me.krumka.onlinebookshop.exception.RegistrationException;
import me.krumka.onlinebookshop.mapper.UserMapper;
import me.krumka.onlinebookshop.model.Role;
import me.krumka.onlinebookshop.model.ShoppingCart;
import me.krumka.onlinebookshop.model.User;
import me.krumka.onlinebookshop.repository.role.RoleRepository;
import me.krumka.onlinebookshop.repository.shoppingcart.ShoppingCartRepository;
import me.krumka.onlinebookshop.repository.user.UserRepository;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@Transactional
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;
    private final UserMapper userMapper;
    private final PasswordEncoder passwordEncoder;
    private final RoleRepository roleRepository;
    private final ShoppingCartRepository shoppingCartRepository;

    @Override
    public UserResponseDto register(UserRegistrationRequestDto userRegistrationRequestDto)
            throws RegistrationException {
        if (userRepository.existsByEmail(userRegistrationRequestDto.getEmail())) {
            throw new RegistrationException("Cannot register user. Email is already in use.");
        }
        User user = userMapper.toUser(userRegistrationRequestDto);
        user.setPassword(passwordEncoder.encode(userRegistrationRequestDto.getPassword()));
        Role.RoleName defaultRoleName = Role.RoleName.ROLE_USER;
        Role defaultRole = roleRepository.findByName(defaultRoleName)
                        .orElseThrow(
                                () -> new EntityNotFoundException(
                                        "Cannot find default role: " + defaultRoleName
                                )
                        );
        user.setRoles(Set.of(defaultRole));
        userRepository.save(user);
        shoppingCartRepository.save(new ShoppingCart(user));
        return userMapper.toUserResponseDto(user);
    }
}
