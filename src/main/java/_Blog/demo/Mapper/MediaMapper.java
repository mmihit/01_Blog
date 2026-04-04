package _Blog.demo.Mapper;

import java.util.List;

import _Blog.demo.DTO.responses.MediaDtoResponse;
import _Blog.demo.models.Entity.Media;
import _Blog.demo.models.Entity.Post;

public class MediaMapper {
    public static Media toMediaEntity(Post post, String mediaUrl, String mediaType) {
        Media media = new Media();
        media.setPost(post);
        media.setMediaUrl(mediaUrl);
        media.setMediaType(mediaType);
        return media;
    }

    static public MediaDtoResponse toMediaDtopResponse(Media media) {
        return MediaDtoResponse.builder()
                .mediaType(media.getMediaType())
                .mediaUrl(media.getMediaUrl())
                .build();
    }

    static public List<MediaDtoResponse> toMediasDtoResponses(List<Media> medias) {
        return medias.stream()
                .map(media -> MediaMapper.toMediaDtopResponse(media))
                .toList();
    }
}
