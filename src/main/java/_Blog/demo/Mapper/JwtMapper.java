package _Blog.demo.Mapper;


import _Blog.demo.DTO.responses.JwtDtoResponse;
import _Blog.demo.jwt.UserPrincipal;
import _Blog.demo.types.Role;

public class JwtMapper {
    static public JwtDtoResponse toJwtDto(UserPrincipal userDetails, String jwt) {
        return JwtDtoResponse.builder()
                .id(userDetails.getId())
                .username(userDetails.getUsername())
                .role(Role.valueOf(userDetails.getAuthorities().iterator().next().toString()))
                .token(jwt)
                .build();
    }
}
