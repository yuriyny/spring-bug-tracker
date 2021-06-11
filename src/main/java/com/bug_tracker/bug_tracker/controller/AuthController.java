package com.bug_tracker.bug_tracker.controller;


import com.bug_tracker.bug_tracker.dto.*;
import com.bug_tracker.bug_tracker.repository.RefreshTokenRequest;
import com.bug_tracker.bug_tracker.service.AuthService;
import com.bug_tracker.bug_tracker.service.RefreshTokenService;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

import static org.springframework.http.HttpStatus.OK;

@RestController
@RequestMapping("/api/auth")
@AllArgsConstructor
public class AuthController {
    private final AuthService authService;
    private final RefreshTokenService refreshTokenService;

    @PostMapping("/login")
    public AuthenticationResponse login(@RequestBody LoginRequest loginRequest) {
        return authService.login(loginRequest);
    }

    @PostMapping("/logout")
    public ResponseEntity<String> logout(@Valid @RequestBody RefreshTokenRequest refreshTokenRequest) {
        refreshTokenService.deleteRefreshToken(refreshTokenRequest.getRefreshToken());
        return ResponseEntity.status(OK).body("Refresh Token Deleted Successfully!");
    }

    @PostMapping("/signup")
    public ResponseEntity<String> signup(@RequestBody RegisterRequest registerRequest) {
        authService.signup(registerRequest);
        return new ResponseEntity<>("User Registration Successful",
                OK);
    }

    @GetMapping("accountVerification/{token}")
    public ResponseEntity<String> verifyAccount(@PathVariable String token) {
        authService.verifyAccount(token);
        return new ResponseEntity<>("Account Activated Successfully", OK);
    }

    @PostMapping("refresh/token")
    public AuthenticationResponse refreshTokens(@Valid @RequestBody RefreshTokenRequest refreshTokenRequest) {
        return authService.refreshToken(refreshTokenRequest);
    }

    @GetMapping("/all-users")
    public List<UserDto> getAllUsers() {
        return authService.getAllUsers();
    }

    @PostMapping("/all-users-not-in-list")
    public List<UserDto> getAllUsersNotInList(@RequestBody List<String> users) {
        return authService.getAllUserNotInTheList(users);
    }

    @GetMapping("/get-user-data")
    public UserDto getCurrentUserData(){
        return authService.getCurrentUserData();
    }

    @PostMapping("/update-user")
    public UserDto UpdateUser(@RequestBody UserDto userDto){
        return authService.updateUser(userDto);
    }

    @PostMapping("/update-password")
    public boolean refreshTokens(@RequestBody UpdatePassword updatePassword) {
        return authService.updateUserPassword(updatePassword);
    }
}
