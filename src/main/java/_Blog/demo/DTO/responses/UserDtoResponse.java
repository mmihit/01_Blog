package _Blog.demo.DTO.responses;

import java.time.LocalDateTime;

import _Blog.demo.models.Entity.User;
import _Blog.demo.types.Role;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UserDtoResponse {
    private String username;
    private String email;
    private Role role;
    private String avatar;
    private String bio;
    private boolean isBanned;
    private LocalDateTime createdAt;

    static public UserDtoResponse toDtoResponse(User user) {
        return UserDtoResponse.builder()
                .username(user.getUsername())
                .email(user.getEmail())
                .role(user.getRole())
                .avatar(user.getAvatar())
                .bio(user.getBio())
                .isBanned(user.isBanned())
                .createdAt(user.getCreatedAt())
                .build();
    }
}
