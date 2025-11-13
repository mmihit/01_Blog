package _Blog.demo.models.DTO;

import java.time.LocalDateTime;

import _Blog.demo.models.Entity.User;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UserDTO {
    private Long id;
    private String username;
    private String email;
    private String password;
    private String role;
    private String avatar;
    private String bio;
    private boolean isBanned;
    private LocalDateTime createdAt;

    static public UserDTO toDTO(User user) {
        return UserDTO.builder()
        .id(user.getId())
        .username(user.getUsername())
        .email(user.getEmail())
        .password(user.getPassword())
        .role(user.getRole())
        .avatar(user.getAvatar())
        .bio(user.getAvatar())
        .isBanned(user.isBanned())
        .createdAt(user.getCreatedAt())
        .build();
    }
}
