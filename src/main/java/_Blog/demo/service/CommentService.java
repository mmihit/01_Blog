package _Blog.demo.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import _Blog.demo.DTO.requests.CommentRequest;
import _Blog.demo.Mapper.CommentMapper;
import _Blog.demo.models.Entity.Comment;
import _Blog.demo.models.Entity.Post;
import _Blog.demo.models.Entity.User;
import _Blog.demo.repository.CommentRepo;
import jakarta.persistence.EntityManager;

@Service
public class CommentService {
    @Autowired
    private CommentRepo commentRepo;

    @Autowired
    private PostService postService;

    @Autowired
    private UserService userService;

    @Autowired
    private EntityManager entityManager;

    public void AddComment(CommentRequest body) {
        Post post = entityManager.getReference(Post.class, body.getPostId());
        User user = entityManager.getReference(User.class, userService.getAuthenticatedUserId());

        if (post == null) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Invalid post Id");
        }
        if (user == null) {
            throw new ResponseStatusException(HttpStatus.CONFLICT,
                    "Can't get current user, please refresh and try again");
        }

        Comment comment = CommentMapper.toCommentEntity(body.getBody(), user, post);
        if (comment != null) {
            commentRepo.save(comment);
        }
    }

    public Comment GetCommentById(Long id) {
        if (id == null) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Invalid comment ID");
        }

        return commentRepo.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.BAD_REQUEST, "Invalid comment ID"));
    }

    public Page<Comment> GetCommentsByPostId(Long postId, Pageable pageable) {
        if (!postService.isPostExists(postId)) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Invalid post Id");
        }

        return commentRepo.findAllByPostId(postId, pageable);
    }
}
