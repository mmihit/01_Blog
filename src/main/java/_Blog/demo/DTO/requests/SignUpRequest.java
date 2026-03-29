package _Blog.demo.DTO.requests;

import java.time.LocalDateTime;

import org.hibernate.validator.constraints.Length;

// import _Blog.demo.models.Entity.User;
import _Blog.demo.types.Role;
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
public class SignUpRequest {
    @NotBlank(message = "username is mandatory")
    @Pattern(regexp = "^[A-Za-z0-9_]{3,20}$", message = "username must contain only letters, numbers and underscores")
    private String username;

    @NotBlank(message = "email is mandatory")
    @Email(message = "email format is incorrect")
    private String email;

    @NotBlank(message = "password is mandatory")
    @Pattern(regexp = "^(?=.*[A-Z])(?=.*[a-z])(?=.*\\d)(?=.*[@$!%*?&]).{8,}$", message = "password must contain uppercase, lowercase, number, special char and be at least 8 characters")
    private String password;

    private Role role;
    private String avatar;

    @Length(message = "The bio must be 1500 charachters at long", max = 1500)
    private String bio;
    private boolean isBanned;
    private LocalDateTime createdAt;

    // static public SignUpRequest toDTO(User user) {
    // return SignUpRequest.builder()
    // .username(user.getUsername())
    // .email(user.getEmail())
    // .password(user.getPassword())
    // .role(user.getRole())
    // .avatar(user.getAvatar())
    // .bio(user.getAvatar())
    // .isBanned(user.isBanned())
    // .createdAt(user.getCreatedAt())
    // .build();
    // }
}
