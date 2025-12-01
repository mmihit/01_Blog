package _Blog.demo.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import _Blog.demo.DTO.requests.LoginRequest;
import _Blog.demo.DTO.requests.SignUpRequest;
import _Blog.demo.DTO.responses.JwtDtoResponse;
import _Blog.demo.jwt.JwtUtils;
import _Blog.demo.models.Entity.User;
import _Blog.demo.repository.UserRepo;

@Service
public class AuthenticationService {

    @Autowired
    private UserAuthService userAuthService;

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private JwtUtils jwtUtils;

    @Autowired
    private UserRepo userRepo;

    public void signUp(SignUpRequest input) {
        User user = User.toEntity(input);

        if (userRepo.existsByusername(input.getUsername())) {
            throw new ResponseStatusException(HttpStatus.CONFLICT, "This Username already exists");
        }
        if (userRepo.existsByemail(input.getEmail())) {
            throw new ResponseStatusException(HttpStatus.CONFLICT, "This Email already exists");
        }

        if (user != null) {
            userRepo.save(user);
        }
    }

    public JwtDtoResponse authenticate(LoginRequest input) {
        Authentication authentication = authenticationManager
                .authenticate(new UsernamePasswordAuthenticationToken(input.getUsername(), input.getPassword()));

        SecurityContextHolder.getContext().setAuthentication(authentication);
        UserDetails userDetails = (UserDetails) authentication.getPrincipal();
        String jwt = jwtUtils.generateToken(userDetails);
        return JwtDtoResponse.toJwtDto(userDetails, jwt);
    }
}
