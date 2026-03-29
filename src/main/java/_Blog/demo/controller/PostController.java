package _Blog.demo.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import _Blog.demo.DTO.requests.PostRequest;
import _Blog.demo.DTO.responses.MediaDtopResponse;
import _Blog.demo.DTO.responses.PostDtopResponse;
import _Blog.demo.models.Entity.Media;
import _Blog.demo.models.Entity.Post;
import _Blog.demo.service.MediaService;
import _Blog.demo.service.PostService;

@RestController
@RequestMapping("/api/post")
public class PostController {
    @Autowired
    private PostService postService;

    @Autowired
    private MediaService mediaService;

    @PostMapping(value = "/create", consumes = { "multipart/form-data" })
    public ResponseEntity<Object> create(@ModelAttribute PostRequest body) {
        postService.create(body);
        return ResponseEntity.ok("The post created succufully.");
    }

    @GetMapping("/{id}")
    public ResponseEntity<Object> getById(@PathVariable Long id) {
        Post post = postService.getPostById(id);
        List<Media> media = mediaService.getMediaByPostId(post.getId());
        List<MediaDtopResponse> mediaResponse = media.stream().map(m->MediaDtopResponse.toMediaDtopResponse(m)).toList();
        return ResponseEntity.ok(PostDtopResponse.toPostDtopResponse(post, mediaResponse));
    }
}
