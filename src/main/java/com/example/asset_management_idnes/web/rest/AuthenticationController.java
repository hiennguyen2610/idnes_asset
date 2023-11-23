package com.example.asset_management_idnes.web.rest;


import com.example.asset_management_idnes.domain.RefreshToken;
import com.example.asset_management_idnes.exception.RefreshTokenNotFoundException;
import com.example.asset_management_idnes.model.request.LoginRequest;
import com.example.asset_management_idnes.model.request.RefreshTokenRequest;
import com.example.asset_management_idnes.model.request.RegistrationRequest;
import com.example.asset_management_idnes.model.response.JwtResponse;
import com.example.asset_management_idnes.repository.RefreshTokenRepository;
import com.example.asset_management_idnes.repository.UserRepository;
import com.example.asset_management_idnes.security.CustomUserDetails;
import com.example.asset_management_idnes.security.JwtUtils;
import com.example.asset_management_idnes.service.UserService;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import java.util.Set;
import java.util.UUID;
import java.util.stream.Collectors;

@CrossOrigin(origins = "http://localhost:4200")
@RestController
@AllArgsConstructor
@RequestMapping("/api/v1/authentication")
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class AuthenticationController {

    JwtUtils jwtUtils;

    UserService userService;

    UserRepository userRepository;

    RefreshTokenRepository refreshTokenRepository;

    AuthenticationManager authenticationManager;

    @PostMapping("/login")
    public JwtResponse authenticateUser(@Valid @RequestBody LoginRequest request, HttpServletResponse response) {
        Authentication authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(request.getEmail(), request.getPassword()));

        SecurityContextHolder.getContext().setAuthentication(authentication);
        String jwt = jwtUtils.generateJwtToken(authentication);

        CustomUserDetails userDetails = (CustomUserDetails) authentication.getPrincipal();
        Set<String> roles = userDetails.getAuthorities().stream()
                .map(GrantedAuthority::getAuthority)
                .collect(Collectors.toSet());

        String refreshToken = UUID.randomUUID().toString();
        RefreshToken refreshTokenEntity = RefreshToken.builder()
                .refreshToken(refreshToken)
                .user(userRepository.findById(userDetails.getId()).get())
                .build();
        refreshTokenRepository.save(refreshTokenEntity);
        return JwtResponse.builder()
                .jwt(jwt)
                .refreshToken(refreshToken)
                .id(userDetails.getId())
                .email(userDetails.getUsername())
                .roles(roles)
                .build();
//        JwtResponse jwtResponse = JwtResponse.builder()
//                .jwt(jwt)
//                .refreshToken(refreshToken)
//                .id(userDetails.getId())
//                .email(userDetails.getUsername())
//                .roles(roles)
////                .name(userDetails.getUsername())
//                .build();
//        Cookie jwtCookie = new Cookie("jwtToken", jwtResponse.getJwt());
//        jwtCookie.setPath("/");
//        response.addCookie(jwtCookie);
//        System.out.println(jwtResponse);
//        return jwtResponse;

    }

    @PostMapping("/signup")
    public ResponseEntity<?> registerUser(@Valid @RequestBody RegistrationRequest request) {
        return userRepository.findByEmail(request.getEmail())
                .map(user -> new ResponseEntity<>("Email is existed", HttpStatus.BAD_REQUEST))
                .orElseGet(() -> {
                    userService.registerUser(request);
                    return new ResponseEntity<>(null, HttpStatus.CREATED);
                });
    }

    @PostMapping("/refresh-token")
    public ResponseEntity<?> refreshToken(@RequestBody @Valid RefreshTokenRequest request, HttpServletResponse response) {
        try {
            return ResponseEntity.ok(userService.refreshToken(request, response));
        } catch (RefreshTokenNotFoundException | UsernameNotFoundException ex) {
            return new ResponseEntity<>("Thông tin refreshToken không chính xác", HttpStatus.BAD_REQUEST);
        }
    }

    @PostMapping("/logout")
    public ResponseEntity<?> logout() {
        userService.logout();
        return ResponseEntity.ok(null);
    }

}
