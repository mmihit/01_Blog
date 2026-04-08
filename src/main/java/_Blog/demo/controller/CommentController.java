package _Blog.demo.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import _Blog.demo.DTO.requests.CommentRequest;
import _Blog.demo.DTO.responses.CommentDtoResponse;
import _Blog.demo.Mapper.CommentMapper;
import _Blog.demo.Mapper.PageMapper;
import _Blog.demo.models.Entity.Comment;
import _Blog.demo.service.CommentService;

@RequestMapping("/api/comment")
@RestController
public class CommentController {
    @Autowired
    private CommentService commentService;

    @PostMapping
    public ResponseEntity<Object> AddComment(@RequestBody CommentRequest body) {
        commentService.AddComment(body);
        return ResponseEntity.ok("Comment added succefully");
    }

    @GetMapping("getById/{id}")
    public ResponseEntity<Object> GetCommentById(@PathVariable Long id) {
        Comment comment = commentService.GetCommentById(id);
        return ResponseEntity.ok(CommentMapper.toCommentDtoResponse(comment));
    }

    @GetMapping("getByPostId/{id}")
    public ResponseEntity<Object> GetCommentsByPostId(@PathVariable Long id,
            @RequestParam(required = false, defaultValue = "1") int nOPage,
            @RequestParam(required = false, defaultValue = "5") int pageSize) {
        Pageable pageable = PageRequest.of(nOPage - 1, pageSize, Sort.by("createdAt").ascending());
        Page<Comment> comments = commentService.GetCommentsByPostId(id, pageable);
        List<CommentDtoResponse> commentDtoResponses = CommentMapper.toCommentsDtoResponses(comments.getContent());
        return ResponseEntity.ok(PageMapper.toPageDtoResponse(commentDtoResponses, comments.hasNext()));
    }
}
