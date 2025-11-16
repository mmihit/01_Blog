package _Blog.demo.models.DTO;

import java.time.LocalDateTime;

import _Blog.demo.models.Entity.User;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Valid
public class UserDTO {
    private Long id;

    @NotBlank(message = "username is mandatory")
    @Pattern(regexp = "^[A-Za-z0-9_]{3,20}$", message = "username must contain only letters, numbers and underscores")
    private String username;

    @NotBlank(message = "email is mandatory")
    @Email(message = "email format is incorrect")
    private String email;

    @NotBlank(message = "password is mandatory")
    @Pattern(regexp = "^(?=.*[A-Z])(?=.*[a-z])(?=.*\\d)(?=.*[@$!%*?&]).{8,}$", message = "password must contain uppercase, lowercase, number, special char and be at least 8 characters")
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
