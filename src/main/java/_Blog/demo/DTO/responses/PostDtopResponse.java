package _Blog.demo.DTO.responses;

import java.util.List;

import _Blog.demo.models.Entity.Post;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class PostDtopResponse {
    private Long id;
    private String title;
    private String body;
    private List<MediaDtopResponse> media;

    static public PostDtopResponse toPostDtopResponse(Post post, List<MediaDtopResponse> mediaDtoResponse) {
        return PostDtopResponse.builder()
                .id(post.getId())
                .title(post.getTitle())
                .body(post.getBody())
                .media(mediaDtoResponse)
                .build();
    }
}
