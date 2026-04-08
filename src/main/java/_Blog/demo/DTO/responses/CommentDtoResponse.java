package _Blog.demo.DTO.responses;

import java.time.LocalDateTime;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class CommentDtoResponse {
    private Long id;
    private UserLiteDtoResponse user;
    private String content;
    private LocalDateTime createdAt;
}
