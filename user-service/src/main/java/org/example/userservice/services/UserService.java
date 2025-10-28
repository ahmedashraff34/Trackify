package org.example.userservice.services;

import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.example.userservice.dtos.UserRequest;
import org.example.userservice.dtos.UserResponse;
import org.example.userservice.mappers.UserMapper;
import org.example.userservice.models.User;
import org.example.userservice.repositories.UserRepository;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    private final UserMapper userMapper;
    private final PasswordEncoder passwordEncoder;

    public List<UserResponse> getAll() {
        return userRepository.findAll().stream().map(userMapper::toUserResponse).toList();
    }

    public UserResponse getById(Long id) {
        return userRepository.findById(id).map(userMapper::toUserResponse)
                .orElseThrow(() -> new IllegalArgumentException("User not found"));
    }

    public UserResponse addUser(UserRequest userRequest) {
        try {
            User user = userMapper.toUser(userRequest);
            user.setPassword(passwordEncoder.encode(userRequest.getPassword()));
            User savedUser = userRepository.save(user);
            return userMapper.toUserResponse(savedUser);
        } catch (DataIntegrityViolationException e) {
            throw new DataIntegrityViolationException("Username or Email already exists");
        }
    }

    public UserResponse updateUser(Long id, UserRequest userRequest) {
        User toUpdate = userRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("User not found"));
        try {
            updateFrom(toUpdate, userRequest);
            userRepository.save(toUpdate);
            return userMapper.toUserResponse(toUpdate);
        } catch (DataIntegrityViolationException e) {
            throw new DataIntegrityViolationException("Username or Email already used");
        }
    }

    public void deleteUser(Long id) {
        try {
            userRepository.deleteById(id);
        } catch (DataIntegrityViolationException e) {
            throw new DataIntegrityViolationException("User not found");
        }
    }

    private void updateFrom(User user, UserRequest userRequest) {
        if (userRequest.getUsername() != null) user.setUsername(userRequest.getUsername());
        if (userRequest.getEmail() != null) user.setEmail(userRequest.getEmail());
        if (userRequest.getPassword() != null && !userRequest.getPassword().isBlank()) {
            user.setPassword(passwordEncoder.encode(userRequest.getPassword()));
        }
        if (userRequest.getFirstName() != null) user.setFirstName(userRequest.getFirstName());
        if (userRequest.getLastName() != null) user.setLastName(userRequest.getLastName());
        if (userRequest.getPhone() != null) user.setPhone(userRequest.getPhone());
        if (userRequest.getAddress() != null) user.setAddress(userRequest.getAddress());
    }


}
