package org.maity.mycelia.user;


import org.maity.mycelia.common.ErrorCode;
import org.maity.mycelia.common.JwtUtil;
import org.maity.mycelia.common.CustomException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class AuthService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtUtil jwtUtil;

    public AuthService(UserRepository userRepository,
                       PasswordEncoder passwordEncoder,
                       JwtUtil jwtUtil) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.jwtUtil = jwtUtil;
    }

    public AuthResponse register(AuthRequest request) {
        if (userRepository.existsByUsername(request.userName())) {
            throw new RuntimeException("User already exists");
        }

        UserEntity user = new UserEntity();
        user.setUserName(request.userName());
        user.setPassword(passwordEncoder.encode(request.password()));
        user.setRole("USER");
        user.setActive(false);
        userRepository.save(user);

        String accessToken = jwtUtil.generateToken(request.userName(), 15 * 60 * 1000); // 15m
        String refreshToken = jwtUtil.generateToken(request.userName(), 7 * 24 * 60 * 60 * 1000); // 7d

        return new AuthResponse(accessToken, refreshToken);
    }

    public AuthResponse login(AuthRequest request) {
        var user = userRepository.findByUserName(request.userName())
                .orElseThrow(() -> new RuntimeException("User not found"));

        if (!passwordEncoder.matches(request.password(), user.getPassword())) {
            throw new CustomException(ErrorCode.INVALID_CREDENTIALS);
        }

        String accessToken = jwtUtil.generateToken(request.userName(), 15 * 60 * 1000);
        String refreshToken = jwtUtil.generateToken(request.userName(), 7 * 24 * 60 * 60 * 1000);

        return new AuthResponse(accessToken, refreshToken);
    }

    public AuthResponse refreshToken(RefreshTokenRequest request) {
        String username = jwtUtil.extractUsername(request.refreshToken());

        if (!jwtUtil.validateToken(request.refreshToken())) {
            throw new CustomException(ErrorCode.INVALID_TOKEN);
        }

        String newAccessToken = jwtUtil.generateToken(username, 15 * 60 * 1000);
        String newRefreshToken = jwtUtil.generateToken(username, 7 * 24 * 60 * 60 * 1000);

        return new AuthResponse(newAccessToken, newRefreshToken);
    }
}
