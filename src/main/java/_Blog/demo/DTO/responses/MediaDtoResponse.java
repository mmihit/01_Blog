package _Blog.demo.DTO.responses;

import _Blog.demo.models.Entity.Media;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class MediaDtoResponse {
    private String mediaType;
    private String mediaUrl;
}
