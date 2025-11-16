package _Blog.demo.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import _Blog.demo.models.DTO.UserDTO;
import _Blog.demo.models.Entity.User;
import _Blog.demo.service.UserService;
import jakarta.validation.Valid;

@RestController
@RequestMapping("/api")
public class UserController {
    @Autowired
    private UserService userSrv;

    @GetMapping("/get_user")
    public @ResponseBody User getUser() {
        return userSrv.getUserByUsername("mohammed");
    }

    @PostMapping("/save_user")
    public ResponseEntity<String> saveUser(@Valid @RequestBody UserDTO user) {
        userSrv.saveUser(user);
        return ResponseEntity.ok("user created successfully");
    }
}
