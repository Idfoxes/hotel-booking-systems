package com.project.HostelBooking.services;

import com.project.HostelBooking.exceptions.UserAlreadyExistException;
import com.project.HostelBooking.exceptions.UserNotFoundException;
import com.project.HostelBooking.mappers.UserMapper;
import com.project.HostelBooking.model.events.RegisterEvent;
import com.project.HostelBooking.model.user.Role;
import com.project.HostelBooking.model.user.User;
import com.project.HostelBooking.repositories.jpa.UserRepository;
import com.project.HostelBooking.utils.BeanUtils;
import com.project.HostelBooking.web.dto.user.UserListResponse;
import com.project.HostelBooking.web.dto.user.UserRequest;
import com.project.HostelBooking.web.dto.user.UserResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.text.MessageFormat;
import java.util.Collections;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    private final UserMapper userMapper;
    private final PasswordEncoder passwordEncoder;
    private final KafkaTemplate<String, RegisterEvent> kafkaTemplate;

    @Value("${app.kafka.kafkaMessageRegisterTopic}")
    private String registerTopic;

    public UserListResponse getAllUsers() {
        return userMapper.userListToResponseList(userRepository.findAll());
    }

    public UserResponse getUserById(Long id) {
        return userMapper.userToResponse(userRepository.findById(id).orElseThrow(
                () -> new UserNotFoundException(
                        MessageFormat.format("Пользователь с id {0} не найден.", id))
        ));
    }

    public User getUserByUsername(String username) {
        return userRepository.findByUsername(username);
    }

    public UserResponse registerUser(UserRequest request) {
        User newUser = userMapper.requestToUser(request);
        checkUserData(newUser);
        Role role = Role.from(request.getRoleType());
        newUser.setPassword(passwordEncoder.encode(request.getPassword()));
        newUser.setRoles(Collections.singletonList(role));
        role.setUser(newUser);
        userRepository.save(newUser);

        RegisterEvent registerEvent = new RegisterEvent();
        registerEvent.setUserId(newUser.getId());
        kafkaTemplate.send(registerTopic, registerEvent);

        return userMapper.userToResponse(newUser);
    }

    private void checkUserData(User newUser){
        if (userRepository.existsByUsername(newUser.getUsername())) {
            throw new UserAlreadyExistException(
                    MessageFormat.format("Пользователь с именем {0} уже существует.", newUser.getUsername()));
        } else if (userRepository.existsByEmail(newUser.getEmail())) {
            throw new UserAlreadyExistException(
                    MessageFormat.format("Пользователь с почтой {0} уже существует.", newUser.getEmail()));
        }
    }

    public UserResponse updateUser(Long id, UserRequest request) {
        User updateUser = userMapper.requestToUser(id, request);
        Optional<User> excitedUser = userRepository.findById(id);

        if (excitedUser.isPresent()) {
            checkUserData(updateUser);
            BeanUtils.copyNonNullProperties(updateUser, excitedUser.get());
            return userMapper.userToResponse(userRepository.save(excitedUser.get()));
        }
        throw new UserNotFoundException(MessageFormat.format("Пользователь с id {0} не найден.", id));
    }

    public void deleteUser(Long id) {
        Optional<User> excitedUser = userRepository.findById(id);
        if (excitedUser.isPresent()) {
            userRepository.deleteById(id);
        } else {
            throw new UserNotFoundException(MessageFormat.format("Пользователь с id {0} не найден.", id));
        }
    }
}
