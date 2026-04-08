package _Blog.demo.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import _Blog.demo.DTO.requests.PostRequest;
import _Blog.demo.DTO.responses.MediaDtoResponse;
import _Blog.demo.DTO.responses.PostDtoResponse;
import _Blog.demo.Mapper.MediaMapper;
import _Blog.demo.Mapper.PageMapper;
import _Blog.demo.Mapper.PostMapper;
import _Blog.demo.models.Entity.Media;
import _Blog.demo.models.Entity.Post;
import _Blog.demo.service.LikeService;
import _Blog.demo.service.MediaService;
import _Blog.demo.service.PostService;
import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/post")
public class PostController {
    @Autowired
    private PostService postService;

    @Autowired
    private MediaService mediaService;

    @Autowired
    private LikeService likeService;

    @PostMapping(value = "/create", consumes = { "multipart/form-data" })
    public ResponseEntity<Object> create(@Valid @ModelAttribute PostRequest body) {
        postService.create(body);
        return ResponseEntity.ok("The post created succufully.");
    }

    @GetMapping("getByPostId/{id}")
    public ResponseEntity<Object> getById(@PathVariable Long id) {
        Post post = postService.getPostById(id);
        List<Media> media = mediaService.getMediaByPostId(post.getId());
        List<MediaDtoResponse> mediaResponse = media.stream().map(m -> MediaMapper.toMediaDtopResponse(m)).toList();
        Long nOfLikes = likeService.NumberOfLikesByPostId(id);
        return ResponseEntity.ok(PostMapper.toPostDtoResponse(post, mediaResponse, nOfLikes));
    }

    @GetMapping("getByUserId/{id}")
    public ResponseEntity<Object> getByUserId(@PathVariable Long id,
            @RequestParam(required = false, defaultValue = "1") int nOpage,
            @RequestParam(required = false, defaultValue = "5") int pagesize) {

        Pageable pageable = PageRequest.of(nOpage - 1, pagesize, Sort.by("createdAt").ascending());
        Page<Post> posts = postService.getPostsByUserIdAndStatus(id, "published", pageable);
        List<PostDtoResponse> postsResponse = posts.getContent().stream()
                        .map(post -> PostMapper.toPostDtoResponse(post,
                                MediaMapper.toMediasDtoResponses(mediaService.getMediaByPostId(post.getId())),
                                likeService.NumberOfLikesByPostId(post.getId())))
                        .toList();
        return ResponseEntity.ok(PageMapper.toPageDtoResponse(postsResponse, posts.hasNext()));
    }
}
