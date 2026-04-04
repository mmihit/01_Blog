package _Blog.demo.DTO.responses;


import _Blog.demo.jwt.UserPrincipal;
import _Blog.demo.types.Role;
import lombok.Builder;
import lombok.Data;

@Builder
@Data
public class JwtDtoResponse {
    private long id;
    private String token;
    private String username;
    private Role role;

}
