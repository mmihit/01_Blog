package _Blog.demo.service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import _Blog.demo.DTO.responses.UserDtoResponse;
import _Blog.demo.jwt.UserPrincipal;
import _Blog.demo.models.Entity.User;
import _Blog.demo.repository.UserRepo;

@Service
public class UserService {

    @Autowired
    UserRepo userRepo;

    public UserDtoResponse getMe() {
        UserPrincipal userPrincipal = (UserPrincipal) SecurityContextHolder.getContext().getAuthentication()
                .getPrincipal();
        Long userId = userPrincipal.getId();
        User user = getUserById(userId);
        return UserDtoResponse.toDtoResponse(user);
    }

    public User getUserByUsername(String username) {
        if (username != null && userRepo != null) {
            Optional<User> data = userRepo.findByusername(username);
            return data.orElseThrow(
                    () -> new ResponseStatusException(HttpStatus.NOT_FOUND, "No user username found with " + username));
        }
        System.err.println("user or userRepo equal null value");
        throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "You should chose a username");
    }

    public User getUserById(Long id) {
        ResponseStatusException internalError = new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR,
                "Something wrong with, please try againe");
        if (id != null && id != 0)
            return userRepo.findById(id).orElseThrow(() -> internalError);
        throw internalError;
    }
}
