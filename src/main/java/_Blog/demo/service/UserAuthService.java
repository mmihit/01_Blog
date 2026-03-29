package _Blog.demo.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import _Blog.demo.jwt.UserPrincipal;
import _Blog.demo.repository.UserRepo;
import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class UserAuthService implements UserDetailsService {

    @Autowired
    private UserRepo userRepo;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return UserPrincipal.toUserPrincipal(userRepo.findByusername(username)
                .orElseThrow(() -> new UsernameNotFoundException("User not found with username" + username)));
    }
}
