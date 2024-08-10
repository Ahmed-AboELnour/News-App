package com.news.controller;

import com.news.config.JwtUtil;
import com.news.entity.BlacklistedToken;
import com.news.entity.User;
import com.news.model.*;
import com.news.repository.TokenBlacklistRepository;
import com.news.service.serviceImp.CustomUserDetailsService;
import com.news.service.serviceImp.UserServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

    @Autowired
    private UserServiceImpl userService;

    @Autowired
    private JwtUtil jwtUtil;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private TokenBlacklistRepository tokenBlacklistRepository;

    @Autowired
    CustomUserDetailsService userDetailsService;

    @PostMapping("/signup")
    public ResponseEntity<Map<String, String>> signup(@Valid @RequestBody UserDto userDto) {
        userService.signUp(userDto);
        Map<String, String> response = new HashMap<>();
        response.put("message", "User registered successfully");
        return ResponseEntity.ok(response);
    }

    @PostMapping("/login")
    public  ResponseEntity<?> login(@Valid @RequestBody AuthenticationResponse authenticationResponse) {
        Authentication authentication = new UsernamePasswordAuthenticationToken(
                authenticationResponse.getEmail(),
                authenticationResponse.getPassword()
        );

        // Authenticate user
        SecurityContextHolder.getContext().setAuthentication(authentication);

        final UserDetails userDetails = userDetailsService.loadUserByUsername(authenticationResponse.getEmail());
        final String accessToken = jwtUtil.generateToken(userDetails.getUsername());

        final String refreshToken = jwtUtil.generateRefreshToken(userDetails.getUsername());

        return ResponseEntity.ok(new AccessTokenResponse(accessToken, refreshToken));
    }


    @PostMapping("/refresh")
    public ResponseEntity<?> refreshToken(@RequestBody RefreshTokenRequest refreshTokenRequest) {
        try {
            String username = jwtUtil.extractUsername(refreshTokenRequest.getRefreshToken());

            if (username != null && jwtUtil.validateToken(refreshTokenRequest.getRefreshToken(), userDetailsService.loadUserByUsername(username))) {
                final String newJwt = jwtUtil.generateToken(username);
                return ResponseEntity.ok(new AccessTokenResponse(newJwt, refreshTokenRequest.getRefreshToken()));
            } else {
                return ResponseEntity.status(403).body("Invalid Refresh Token");
            }
        } catch (Exception e) {
            return ResponseEntity.status(403).body("Invalid Refresh Token");
        }
    }


    @PostMapping("/logout")
    public ResponseEntity<Map<String, String>> logout(HttpServletRequest request) {
        String token = request.getHeader("Authorization").substring(7);
        Date expiryDate = jwtUtil.getClaimsFromToken(token).getExpiration();
        SecurityContextHolder.clearContext();

        BlacklistedToken blacklistedToken = new BlacklistedToken(token, expiryDate);
        tokenBlacklistRepository.save(blacklistedToken);

        Map<String, String> response = new HashMap<>();
        response.put("message", "Logged out successfully");
        return ResponseEntity.ok(response);
    }
}
