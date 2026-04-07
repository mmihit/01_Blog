package _Blog.demo.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.server.ResponseStatusException;

import _Blog.demo.DTO.requests.LoginRequest;
import _Blog.demo.DTO.requests.SignUpRequest;
import _Blog.demo.DTO.responses.JwtDtoResponse;
import _Blog.demo.Mapper.JwtMapper;
import _Blog.demo.Mapper.UserMapper;
import _Blog.demo.jwt.JwtUtils;
import _Blog.demo.jwt.UserPrincipal;
import _Blog.demo.models.Entity.User;
import _Blog.demo.repository.UserRepo;

@Service
public class AuthenticationService {

    @Autowired
    private FileStorageService fileStorageService;

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private JwtUtils jwtUtils;

    @Autowired
    private UserRepo userRepo;

    public void signUp(SignUpRequest input) {

        if (userRepo.existsByusername(input.getUsername())) {
            throw new ResponseStatusException(HttpStatus.CONFLICT, "This Username already exists");
        }
        if (userRepo.existsByemail(input.getEmail())) {
            throw new ResponseStatusException(HttpStatus.CONFLICT, "This Email already exists");
        }

        MultipartFile avatar = input.getAvatar();
        String avatarpathString = null;
        if (avatar != null && !avatar.isEmpty()) {
            avatarpathString = fileStorageService.uploadFile(avatar, fileStorageService.getTypeOfFile(avatar),
                    "avatar");
        }
        User user = UserMapper.toUserEntity(input, avatarpathString);
        if (user!=null) {
            userRepo.save(user);
        }

    }

    public JwtDtoResponse authenticate(LoginRequest input) {
        Authentication authentication = authenticationManager
                .authenticate(new UsernamePasswordAuthenticationToken(input.getUsername(), input.getPassword()));

        SecurityContextHolder.getContext().setAuthentication(authentication);
        UserPrincipal userPrincipal = (UserPrincipal) authentication.getPrincipal();
        String jwt = jwtUtils.generateToken(userPrincipal);
        return JwtMapper.toJwtDto(userPrincipal, jwt);
    }
}
