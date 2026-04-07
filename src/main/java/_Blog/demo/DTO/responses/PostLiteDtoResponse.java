package _Blog.demo.DTO.responses;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class PostLiteDtoResponse {
    private Long id;
    private UserLiteDtoResponse creator;
    private String title;
    private String body;
}
