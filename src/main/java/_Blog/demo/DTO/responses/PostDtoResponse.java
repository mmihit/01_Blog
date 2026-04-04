package _Blog.demo.DTO.responses;

import java.util.List;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class PostDtoResponse {
    private Long id;
    private String title;
    private String body;
    private List<MediaDtoResponse> media;
}
