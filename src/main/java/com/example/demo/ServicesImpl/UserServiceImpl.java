package com.example.demo.ServicesImpl;

import java.time.LocalDateTime;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.example.demo.Repository.UserRepository;
import com.example.demo.Services.UserService;
import com.example.demo.entity.User;
import com.example.demo.sequence.SequenceGeneratorService;

@Service
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
    private final SequenceGeneratorService sequenceGenerator;

    public UserServiceImpl(UserRepository userRepository,
                           SequenceGeneratorService sequenceGenerator) {
        this.userRepository = userRepository;
        this.sequenceGenerator = sequenceGenerator;
    }

    @Override
    public User registerUser(User user) {

        if (userRepository.existsByUsername(user.getUsername())) {
            throw new IllegalArgumentException("Username already taken");
        }

        if (userRepository.existsByEmail(user.getEmail())) {
            throw new IllegalArgumentException("Email already registered");
        }

        user.setUserId(sequenceGenerator.generateSequence(User.SEQUENCE_NAME));

        user.setPassword(passwordEncoder.encode(user.getPassword()));
        user.setCreatedAt(LocalDateTime.now());

        return userRepository.save(user);
    }
}
