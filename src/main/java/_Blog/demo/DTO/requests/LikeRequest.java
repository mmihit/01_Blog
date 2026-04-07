package _Blog.demo.DTO.requests;

import _Blog.demo.types.LikeType;
import lombok.Data;

@Data
public class LikeRequest {
    private LikeType action;
    private Long postId;
}
