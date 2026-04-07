package _Blog.demo.DTO.responses;

import java.time.LocalDateTime;

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
    private long id;
    private String username;
    private String email;
    private Role role;
    private String avatar;
    private String bio;
    private boolean isBanned;
    private LocalDateTime createdAt;
}
