package _Blog.demo.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.server.ResponseStatusException;

import _Blog.demo.DTO.requests.PostRequest;
import _Blog.demo.Mapper.PostMapper;
import _Blog.demo.models.Entity.Post;
import _Blog.demo.models.Entity.User;
import _Blog.demo.repository.PostRepo;
import jakarta.persistence.EntityManager;

@Service
public class PostService {
    @Autowired
    private PostRepo postRepo;

    @Autowired
    private MediaService mediaService;

    @Autowired
    private UserService userService;

    @Autowired
    private EntityManager entityManager;

    @Transactional
    public void create(PostRequest body) {
        Long postUser = userService.getAuthenticatedUserId();
        User userRef = entityManager.getReference(User.class, postUser);
        Post post = PostMapper.toPostEntity(body, userRef);
        if (post == null) {
            throw new ResponseStatusException(HttpStatus.CONFLICT, "Cannot create post");
        }
        Post savedPost = postRepo.save(post);
        List<MultipartFile> mediaFiles = body.getMediaFile();

        if (mediaFiles != null && mediaFiles.size() >0) {
            mediaFiles.stream().forEach(mediaFile -> mediaService.create(savedPost, mediaFile));
        }
    }

    @Transactional
    public Post getPostById(Long id) {
        if (id == null || id <= 0) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Invalid post ID");
        }
        return postRepo.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Post not found"));
    }

    public List<Post> getPostsByUserIdAndStatus(Long id, String status,Pageable pageable) {
        if (!userService.userExistsById(id)) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Invalid user id");
        }

        return postRepo.findAllByUserIdAndStatus(id, status, pageable);
    }

    public boolean isPostExists(Long id) {
        if (id == null) {
            return false;
        }
        return postRepo.existsById(id);
    }
}
