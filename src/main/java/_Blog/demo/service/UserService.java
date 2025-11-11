package _Blog.demo.service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import _Blog.demo.models.User;
import _Blog.demo.repository.UserRepo;

@Service
public class UserService {
    @Autowired
    UserRepo userRepo;

    public User getUserByUsername(String username) {
        if (username != null && userRepo!=null) {
            Optional<User> data = userRepo.findbyusername(username);
            if (data.isPresent()) {
                return data.get();
            }
            return null;
        } else {
            System.err.println("user or serRepo equal null value");
            return null;
        }
    }
}
