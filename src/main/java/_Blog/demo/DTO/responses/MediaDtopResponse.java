package _Blog.demo.DTO.responses;

import _Blog.demo.models.Entity.Media;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class MediaDtopResponse {
    private String mediaType;
    private String mediaUrl;

    static public MediaDtopResponse toMediaDtopResponse(Media media) {
        return MediaDtopResponse.builder()
                .mediaType(media.getMediaType())
                .mediaUrl(media.getMediaUrl())
                .build();
    }
}
