package _Blog.demo.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import _Blog.demo.Mapper.FollowMapper;
import _Blog.demo.models.Entity.Follow;
import _Blog.demo.models.Entity.User;
import _Blog.demo.repository.FollowRepo;
import jakarta.persistence.EntityManager;
import lombok.Builder;

@Service
@Builder
public class FollowService {

    @Autowired
    private UserService userService;
    private EntityManager entityManager;
    @Autowired
    private FollowRepo followRepo;

    public void followingUser(Long followingId) {
        if (followingId == null || followingId <= 0 || userService.userExistsById(followingId)) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "following user ID is invalid");
        }
        
        Long authenticationUserId = userService.getAuthenticatedUserId();
        if (followingId.equals(authenticationUserId)) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "You cannot follow yourself");
        }

        if (isFollowing(followingId, authenticationUserId)) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "You are already following this user");
        }

        User FollowerUser = entityManager.getReference(User.class, authenticationUserId);
        User FollowingUser = entityManager.getReference(User.class, followingId);
        Follow followEntity = FollowMapper.toFollowEntity(FollowingUser, FollowerUser);

        if (!FollowerUser.equals(null) && !FollowingUser.equals(null) && !followEntity.equals(null)) {
            followRepo.save(followEntity);
        } else
            throw new ResponseStatusException(HttpStatus.CONFLICT, "Cannot follow this user");
    }

    @Transactional
    public void unfollowingUser(Long followingId) {
        if (followingId == null || followingId <= 0 || userService.userExistsById(followingId)) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "following user ID is invalid");
        }

        Long authenticationUserId = userService.getAuthenticatedUserId();
        if (followingId.equals(authenticationUserId)) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "You cannot unfollow yourself");
        }

        if (!userService.userExistsById(followingId)) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND,
                    "The user you are trying to unfollow does not exist");
        }

        if (!isFollowing(followingId, authenticationUserId)) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "You are not following this user");
        }
        followRepo.deleteByFollowingIdAndFollowerId(followingId, authenticationUserId);
    }

    public List<User> getFollowers(Long id) {
        if (!userService.userExistsById(id)) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "User not found with this id");
        }
        return followRepo.findAllByFollowingId(id)
                .stream()
                .map(follow -> follow.getFollower())
                .toList();
    }

    public List<User> getFollowing(Long id) {
        if (!userService.userExistsById(id)) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "User not found with this id");
        }
        return followRepo.findAllByFollowerId(id)
                .stream()
                .map(follow -> follow.getFollowing())
                .toList();
    }

    public Boolean isFollowing(Long followingId, Long followerId) {
        return followRepo.existsByFollowingIdAndFollowerId(followingId, followerId);
    }
}
