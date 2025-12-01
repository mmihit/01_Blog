package _Blog.demo.configuration;

import _Blog.demo.jwt.AuthEntryPointJwt;
import _Blog.demo.jwt.AuthTokenFilter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
@EnableMethodSecurity
public class SecurityConfig {

    private final AuthTokenFilter jwtAuthFilter;
    private final AuthenticationProvider authenticationProvider;
    private final AuthEntryPointJwt unauthorizedHandler;

    // We inject the beans created in ApplicationConfig and the Filter component
    public SecurityConfig(AuthTokenFilter jwtAuthFilter, 
                          AuthenticationProvider authenticationProvider,
                          AuthEntryPointJwt unauthorizedHandler) {
        this.jwtAuthFilter = jwtAuthFilter;
        this.authenticationProvider = authenticationProvider;
        this.unauthorizedHandler = unauthorizedHandler;
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
            .csrf(AbstractHttpConfigurer::disable) // Disable CSRF for JWT usage
            .exceptionHandling(exception -> exception
                .authenticationEntryPoint(unauthorizedHandler) // Handle 401 errors
            )
            .authorizeHttpRequests(auth -> auth
                .requestMatchers("/api/auth/**").permitAll() // Whitelist login/register
                .requestMatchers("/h2-console/**").permitAll() // Whitelist H2 console
                .anyRequest().authenticated() // Protect everything else
            )
            .sessionManagement(session -> session
                .sessionCreationPolicy(SessionCreationPolicy.STATELESS) // No sessions
            )
            .authenticationProvider(authenticationProvider) // Use your custom UserDetailsService
            .addFilterBefore(jwtAuthFilter, UsernamePasswordAuthenticationFilter.class); // Add your filter

        // Fix for H2 Console display issues
        http.headers(headers -> headers.frameOptions(frame -> frame.sameOrigin()));

        return http.build();
    }
}