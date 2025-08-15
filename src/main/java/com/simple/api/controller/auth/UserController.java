package com.simple.api.controller.auth;

import com.simple.api.controller.auth.dto.JwtAuthenticationResponse;
import com.simple.api.ResponseMessage;
import com.simple.api.controller.auth.dto.RefreshTokenRequest;
import com.simple.domain.auth.dto.UserDto;
import com.simple.domain.auth.model.User;
import com.simple.bootstrap.jwt.JwtTokenProvider;
import com.simple.usecase.auth.UserUseCase;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class UserController {

    private final AuthenticationManager authenticationManager;
    private final JwtTokenProvider tokenProvider;
    private final UserUseCase userUseCase;

    @PostMapping("/login")
    public ResponseMessage<JwtAuthenticationResponse>
    login(@RequestParam String email, @RequestParam String password) {
        Authentication authentication =
                authenticationManager.authenticate(
                        new UsernamePasswordAuthenticationToken(email, password));

        String accessToken = tokenProvider.createAccessToken(authentication);
        String refreshToken = tokenProvider.createRefreshToken(authentication);

        return ResponseMessage.success(new JwtAuthenticationResponse(accessToken, refreshToken));
    }

    @PostMapping("/refresh")
    public ResponseMessage<JwtAuthenticationResponse>
    refreshToken(@RequestBody RefreshTokenRequest request) {
        String newAccessToken = tokenProvider.refreshAccessToken(request.getRefreshToken());
        return ResponseMessage.success(new JwtAuthenticationResponse(newAccessToken, null));
    }

    @PostMapping("/signup")
    public ResponseMessage<User>
    singUp(@Validated @RequestBody UserDto user){
        User userNew = userUseCase.singUp(user);
        return ResponseMessage.success(userNew);
    }

    @GetMapping("/{userId}")
    public ResponseMessage<User>
    proFile(@PathVariable String userId){
        User userNew = userUseCase.getUserById(userId);
        return ResponseMessage.success(userNew);
    }

    @PutMapping
    public ResponseMessage<User>
    update(@Validated @RequestBody UserDto user){
        User userNew = userUseCase.updateUser(user);
        return ResponseMessage.success(userNew);
    }

    @DeleteMapping("/{userId}")
    public ResponseMessage<User>
    delete(@PathVariable String userId){
        userUseCase.deleteUserById(userId);
        return ResponseMessage.success();
    }
}