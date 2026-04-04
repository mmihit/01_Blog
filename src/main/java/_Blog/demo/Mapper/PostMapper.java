package _Blog.demo.Mapper;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import _Blog.demo.DTO.requests.PostRequest;
import _Blog.demo.DTO.responses.MediaDtoResponse;
import _Blog.demo.DTO.responses.PostDtoResponse;
import _Blog.demo.models.Entity.Post;
import _Blog.demo.models.Entity.User;
import _Blog.demo.repository.MediaRepo;
import _Blog.demo.service.MediaService;
import _Blog.demo.service.PostService;

@Component
public class PostMapper {

        @Autowired
        private MediaService mediaService;

        public static Post toPostEntity(PostRequest postRequest, User user) {
                return Post.builder()
                                .title(postRequest.getTitle())
                                .body(postRequest.getBody())
                                .user(user)
                                .build();
        }

        static public PostDtoResponse toPostDtoResponse(Post post, List<MediaDtoResponse> mediaDtoResponse) {
                if (post == null)
                        return null;
                if (mediaDtoResponse == null)
                        mediaDtoResponse = new ArrayList<MediaDtoResponse>();
                return PostDtoResponse.builder()
                                .id(post.getId())
                                .title(post.getTitle())
                                .body(post.getBody())
                                .media(mediaDtoResponse)
                                .build();
        }

        // static public List<PostDtoResponse> toPostsDtoResponse(List<Post> posts) {
        //         // mediaService.getMediaByPostId()
        //         // MediaService mediaService = new MediaService();
        //         return posts.stream()
        //                         .map(post -> PostMapper.toPostDtoResponse(post,
        //                                         MediaMapper.toMediasDtoResponses(
        //                                                         mediaService.getMediaByPostId(post.getId()))))
        //                         .toList();
        // }
}
