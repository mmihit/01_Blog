package _Blog.demo.DTO.requests;

import java.util.List;

import org.hibernate.validator.constraints.Length;
import org.springframework.web.multipart.MultipartFile;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
@Valid
public class PostRequest {
    @NotBlank(message = "title is mandatory")
    @Length(message = "the maximum title length is 150", max = 150)
    private String title;

    @NotBlank(message = "title is mandatory")
    @Length(message = "the maximum title length is 3000", max = 3000)
    private String body;
    
    private List<MultipartFile> mediaFile;
}
