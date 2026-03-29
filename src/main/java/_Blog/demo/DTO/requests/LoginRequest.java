package _Blog.demo.DTO.requests;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
@Valid
public class LoginRequest {
    @NotBlank(message = "username is mandatory")
    private String username; 

    @NotBlank(message = "password is mandatory")
    private String password;
}
