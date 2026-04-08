package _Blog.demo.service;

import java.net.http.HttpResponse;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import _Blog.demo.DTO.requests.LikeRequest;
import _Blog.demo.Mapper.LikeMapper;
import _Blog.demo.models.Entity.Like;
import _Blog.demo.models.Entity.Post;
import _Blog.demo.models.Entity.User;
import _Blog.demo.repository.LikeRepository;
import jakarta.persistence.EntityManager;

@Service
public class LikeService {

    @Autowired
    private LikeRepository likeRepo;

    @Autowired
    private PostService postService;

    @Autowired
    private UserService userService;

    @Autowired
    private EntityManager entityManager;

    @Transactional
    public void AddLike(Long postId) {
        Post post = entityManager.getReference(Post.class, postId);
        User user = entityManager.getReference(User.class, userService.getAuthenticatedUserId());

        if (post == null) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Invalid Post Id");
        }

        if (user==null) {
            throw new ResponseStatusException(HttpStatus.CONFLICT, "Can't get current user, please refresh and try again");
        }

        Like like = LikeMapper.toEntity(post, user);

        if (like!=null) {
            likeRepo.save(like);
        }
    }

    @Transactional
    public void DeleteLike(Long postId) {
        Long userId = userService.getAuthenticatedUserId();
        likeRepo.deleteByPostIdAndUserId(postId, userId);
    }

    public Long NumberOfLikesByPostId(Long postId) {
        if (!postService.isPostExists(postId)){
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Invalid post Id");
        }

        return likeRepo.countByPostId(postId);
    }

    public Page<User> GetLikersByPostId(Long postId, Pageable pageable) {
        if (!postService.isPostExists(postId)) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Invalid post id");
        }

        return likeRepo.findAllUsersByPostId(postId, pageable);
    }
}
