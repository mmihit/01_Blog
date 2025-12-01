package _Blog.demo.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import _Blog.demo.DTO.requests.LoginRequest;
import _Blog.demo.DTO.requests.SignUpRequest;
import _Blog.demo.DTO.responses.JwtDtoResponse;
import _Blog.demo.service.AuthenticationService;
import _Blog.demo.service.UserService;
import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/auth")
public class AuthController {
    @Autowired
    private UserService userSrv;

    @Autowired
    private AuthenticationService authenticationService;

    @PostMapping("/sign_up")
    public ResponseEntity<String> signUp(@Valid @RequestBody SignUpRequest user) {
        authenticationService.signUp(user);
        return ResponseEntity.ok("user created successfully");
    }

    @PostMapping("/sign_in")
    public ResponseEntity<JwtDtoResponse> authenticateUser(@Valid @RequestBody LoginRequest LoginRequest) {
        return ResponseEntity.ok(authenticationService.authenticate(LoginRequest));
    }
}
