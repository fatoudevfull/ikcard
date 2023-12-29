package com.BackendIkcard.IkcardBackend.Configuration.SpringSecurity.Services;


import com.BackendIkcard.IkcardBackend.Message.Exeption.TokenRefreshException;
import com.BackendIkcard.IkcardBackend.Models.RefreshToken;
import com.BackendIkcard.IkcardBackend.Models.Users;
import com.BackendIkcard.IkcardBackend.Repository.RefreshTokenRepository;
import com.BackendIkcard.IkcardBackend.Repository.UsersRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Instant;
import java.util.Optional;
import java.util.UUID;

@Service
public class RefreshTokenService {

    @Value("${etulon.app.jwtRefreshExpirationMs}")
    private Long refreshTokenDurationMs;

    @Autowired
    private RefreshTokenRepository refreshTokenRepository;

    @Autowired
    private UsersRepository userRepository;

    public Optional<RefreshToken> findByToken(String token) {
        return refreshTokenRepository.findByToken(token);
    }

    public RefreshToken createRefreshToken(Long id) {
        RefreshToken refreshToken = new RefreshToken();

        // Retrieve the user from the repository and set it in the refresh token
        Users user = userRepository.findById(id).orElse(null);
        refreshToken.setUser(user);

        // Generate a unique token
        refreshToken.setToken(UUID.randomUUID().toString());

        // Set the expiry date
        refreshToken.setExpiryDate(Instant.now().plusMillis(refreshTokenDurationMs));

        // Save the refresh token
        refreshToken = refreshTokenRepository.save(refreshToken);

        // Print the values for debugging
        System.out.println("le user: ");
        System.out.println(refreshToken.getUser());
        System.out.println("le token: ");
        System.out.println(refreshToken.getToken());

        return refreshToken;
    }


    public RefreshToken verifyExpiration(RefreshToken token) {
        if (token.getExpiryDate().compareTo(Instant.now()) < 0) {
            refreshTokenRepository.delete(token);
            throw new TokenRefreshException(token.getToken(), "Refresh token was expired. Please make a new signin request");
        }

        return token;
    }

    @Transactional
    public int deleteByUserId(Long userId) {
        return refreshTokenRepository.deleteByUser(userRepository.findById(userId).get());
    }
}
    

