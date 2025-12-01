package _Blog.demo.configuration;

import _Blog.demo.repository.UserRepo;
import _Blog.demo.service.UserAuthService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

@Configuration
public class ApplicationConfig {

    private final UserRepo userRepository;

    // Use constructor injection for dependencies
    public ApplicationConfig(UserRepo userRepository) {
        this.userRepository = userRepository;
    }

    // 1. UserDetailsService Bean
    // Spring Security needs this to know where to load user data from.
    // It uses your custom UserAuthService implementation.
    @Bean
    public UserDetailsService userDetailsService() {
        return new UserAuthService(userRepository);
    }

    // 2. PasswordEncoder Bean
    // Spring Security needs this to hash passwords (during signup) 
    // and verify them (during sign-in).
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    // 3. AuthenticationProvider Bean (The missing piece)
    // This provider ties the UserDetailsService and the PasswordEncoder together.
    @Bean
    public AuthenticationProvider authenticationProvider() {
        DaoAuthenticationProvider authProvider = new DaoAuthenticationProvider();
        // Set your custom user details service
        authProvider.setUserDetailsService(userDetailsService());
        // Set your password encoder
        authProvider.setPasswordEncoder(passwordEncoder());
        return authProvider;
    }
    
    // 4. AuthenticationManager Bean
    // This is often required for the sign-in endpoint (AuthService) to trigger authentication.
    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration config) throws Exception {
        return config.getAuthenticationManager();
    }
}