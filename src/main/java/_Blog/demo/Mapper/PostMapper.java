package _Blog.demo.Mapper;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Component;

import _Blog.demo.DTO.requests.PostRequest;
import _Blog.demo.DTO.responses.MediaDtoResponse;
import _Blog.demo.DTO.responses.PostDtoResponse;
import _Blog.demo.DTO.responses.PostLiteDtoResponse;
import _Blog.demo.DTO.responses.UserLiteDtoResponse;
import _Blog.demo.models.Entity.Post;
import _Blog.demo.models.Entity.User;

@Component
public class PostMapper {

        // @Autowired
        // private MediaService mediaService;

        public static Post toPostEntity(PostRequest postRequest, User user) {
                return Post.builder()
                                .title(postRequest.getTitle())
                                .body(postRequest.getBody())
                                .user(user)
                                .build();
        }

        static public PostDtoResponse toPostDtoResponse(Post post, List<MediaDtoResponse> mediaDtoResponse,
                        Long nOfLikes) {
                if (post == null)
                        return null;
                if (mediaDtoResponse == null)
                        mediaDtoResponse = new ArrayList<MediaDtoResponse>();
                return PostDtoResponse.builder()
                                .id(post.getId())
                                .title(post.getTitle())
                                .body(post.getBody())
                                .media(mediaDtoResponse)
                                .numberOfLikes(nOfLikes)
                                .build();
        }

        static public PostLiteDtoResponse toPostLiteDtoResponse(Post post, UserLiteDtoResponse user) {
                if (post == null)
                        return null;
                return PostLiteDtoResponse.builder()
                                .id(post.getId())
                                .title(post.getBody())
                                .creator(user)
                                .build();
        }

        // static public List<PostDtoResponse> toPostsDtoResponse(List<Post> posts) {
        // // mediaService.getMediaByPostId()
        // // MediaService mediaService = new MediaService();
        // return posts.stream()
        // .map(post -> PostMapper.toPostDtoResponse(post,
        // MediaMapper.toMediasDtoResponses(
        // mediaService.getMediaByPostId(post.getId()))))
        // .toList();
        // }
}
