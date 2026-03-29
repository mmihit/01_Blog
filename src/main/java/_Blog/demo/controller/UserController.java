package _Blog.demo.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import _Blog.demo.DTO.responses.UserDtoResponse;
import _Blog.demo.service.UserService;

@RestController
@RequestMapping("/api/user")
public class UserController {

    @Autowired
    private UserService userService;

    @GetMapping("/me")
    public ResponseEntity<UserDtoResponse> getMe() {
        return ResponseEntity.ok(UserDtoResponse.toDtoResponse(userService.getMe()));
    }

    @GetMapping("/{username}")
    public ResponseEntity<UserDtoResponse> getUserByUsername(@PathVariable String username) {
        return ResponseEntity.ok(UserDtoResponse.toDtoResponse(userService.getUserByUsername(username)));
    }

}