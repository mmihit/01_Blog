package _Blog.demo.service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;
import _Blog.demo.jwt.UserPrincipal;
import _Blog.demo.models.Entity.User;
import _Blog.demo.repository.UserRepo;

@Service
public class UserService {

    @Autowired
    UserRepo userRepo;

    public Long getAuthenticatedUserId() {
        Object principal = SecurityContextHolder.getContext().getAuthentication()
                .getPrincipal();
        if (!(principal instanceof UserPrincipal userPrincipal))
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "Unauthorized");
        Long userId = userPrincipal.getId();
        return userId;
    }

    public User getMe() {
        Long userId = getAuthenticatedUserId();
        return getUserById(userId);
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

    public boolean userExistsById(Long id) {
        if (id != null && id > 0)
            return userRepo.existsById(id);
        return false;
    }

    public String getUsernameById(Long id) {
        if (id != null && id > 0 && userExistsById(id)) {
            return userRepo.findUsernameById(id).orElseThrow(
                    () -> new ResponseStatusException(HttpStatus.BAD_REQUEST, "There is no user with this Id"));
        }
        throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Id is invalid");
    }

    public Long getUserIdByUsername(String username) {
        if (username != null && !username.isEmpty()) {
            if (username.equals("me")) return getAuthenticatedUserId();
            return userRepo.findByUsername(username).orElseThrow(
                    () -> new ResponseStatusException(HttpStatus.BAD_REQUEST, "There is no user with this username")).getId();
        }
        throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "The username is invalid");
    }
}
