package _Blog.demo.DTO.responses;

import org.springframework.security.core.userdetails.UserDetails;

import _Blog.demo.types.Role;
import lombok.Builder;
import lombok.Data;

@Builder
@Data
public class JwtDtoResponse {
    private String token;
    private String username;
    private Role role;

    static public JwtDtoResponse toJwtDto(UserDetails userDetails, String jwt) {
        return JwtDtoResponse.builder().username(userDetails.getUsername()).role(Role.valueOf(userDetails.getAuthorities().iterator().next().toString())).token(jwt).build();
    }
}
