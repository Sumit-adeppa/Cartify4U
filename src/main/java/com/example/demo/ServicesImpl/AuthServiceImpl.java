package com.example.demo.ServicesImpl;

import java.nio.charset.StandardCharsets;
import java.security.Key;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.example.demo.Repository.JWTTokenRepository;
import com.example.demo.Repository.UserRepository;
import com.example.demo.Services.AuthService;
import com.example.demo.entity.JWTToken;
import com.example.demo.entity.User;
import com.example.demo.sequence.SequenceGeneratorService;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;

@Service
public class AuthServiceImpl implements AuthService {

    private final Key SIGNING_KEY;

    private final UserRepository userRepository;
    private final JWTTokenRepository jwtTokenRepository;
    private final BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
    private final SequenceGeneratorService sequenceGenerator;

    public AuthServiceImpl(UserRepository userRepository,
                           JWTTokenRepository jwtTokenRepository,
                           SequenceGeneratorService sequenceGenerator,
                           @Value("${jwt.secret}") String jwtSecret) {

        this.userRepository = userRepository;
        this.jwtTokenRepository = jwtTokenRepository;
        this.sequenceGenerator = sequenceGenerator;

        if (jwtSecret.getBytes(StandardCharsets.UTF_8).length < 64) {
            throw new IllegalArgumentException("JWT_SECRET must be at least 64 bytes for HS512");
        }

        this.SIGNING_KEY = Keys.hmacShaKeyFor(jwtSecret.getBytes(StandardCharsets.UTF_8));
    }

    @Override
    public User authenticate(String username, String password) {

        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new RuntimeException("Invalid username or password"));

        if (!passwordEncoder.matches(password, user.getPassword())) {
            throw new RuntimeException("Invalid username or password");
        }

        return user;
    }

    @Override
    public String generateToken(User user) {

        LocalDateTime now = LocalDateTime.now();
        Optional<JWTToken> existing = jwtTokenRepository.findByUserId(user.getUserId());

        if (existing.isPresent() && now.isBefore(existing.get().getExpireAt())) {
            return existing.get().getToken();
        }

        String token = Jwts.builder()
                .setSubject(user.getUsername())
                .claim("role", user.getRole().name())
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis() + 3600000))
                .signWith(SIGNING_KEY, SignatureAlgorithm.HS512)
                .compact();

        saveToken(user, token);
        return token;
    }

    @Override
    public void saveToken(User user, String token) {

        jwtTokenRepository.deleteByUserId(user.getUserId());

        JWTToken jwtToken = new JWTToken();
        jwtToken.setTokenId(sequenceGenerator.generateSequence(JWTToken.SEQUENCE_NAME));
        jwtToken.setUserId(user.getUserId());
        jwtToken.setToken(token);
        jwtToken.setExpireAt(LocalDateTime.now().plusHours(1));

        jwtTokenRepository.save(jwtToken);
    }

    @Override
    public void logout(User user) {
        jwtTokenRepository.deleteByUserId(user.getUserId());
    }

    @Override
    public boolean validateToken(String token) {
        try {
            Jwts.parserBuilder()
                    .setSigningKey(SIGNING_KEY)
                    .build()
                    .parseClaimsJws(token);

            Optional<JWTToken> jwtToken = jwtTokenRepository.findByToken(token);
            return jwtToken.isPresent() &&
                   jwtToken.get().getExpireAt().isAfter(LocalDateTime.now());

        } catch (Exception e) {
            return false;
        }
    }

    @Override
    public String extractUsername(String token) {
        return Jwts.parserBuilder()
                .setSigningKey(SIGNING_KEY)
                .build()
                .parseClaimsJws(token)
                .getBody()
                .getSubject();
    }
}
