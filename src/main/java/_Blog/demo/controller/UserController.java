package _Blog.demo.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import _Blog.demo.models.User;
import _Blog.demo.service.UserService;

@RestController
@RequestMapping("/")
public class UserController {
    @Autowired
    private UserService userSrv;

    @GetMapping("/api")
    public @ResponseBody User Main() {
        User user = userSrv.getUserByUsername("mohammed");

        return user;
    }
}
