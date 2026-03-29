package _Blog.demo.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import _Blog.demo.DTO.responses.UserDtoResponse;
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
    @Autowired
    private EntityManager entityManager;
    private FollowRepo followRepo;

    public void followingUser(Long followingId) {
        Long authenticationUserId = userService.getAuthenticatedUserId();

        if (followingId.equals(authenticationUserId)) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "You cannot follow yourself");
        }

        if (isFollowing(followingId, authenticationUserId)) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "You are already following this user");
        }

        User FollowerUser = entityManager.getReference(User.class, authenticationUserId);
        User FollowingUser = entityManager.getReference(User.class, followingId);
        Follow followEntity = Follow.toFollowEntity(FollowingUser, FollowerUser);

        if (!FollowerUser.equals(null) && !FollowingUser.equals(null) && !followEntity.equals(null)) {
            followRepo.save(followEntity);
        } else
            throw new ResponseStatusException(HttpStatus.CONFLICT, "Cannot follow this user");
    }

    @Transactional
    public void unfollowingUser(Long followingId) {
        Long authenticationUserId = userService.getAuthenticatedUserId();
        if (followingId == null || followingId <= 0) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "following ID is invalid");
        }

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

    public List<UserDtoResponse> getFollowers(String username) {
        Long userId = userService.getUserIdByUsername(username);
        return followRepo.findAllByFollowingId(userId).stream().map(follow->UserDtoResponse.toDtoResponse(follow.getFollower())).toList();
    }

    public List<UserDtoResponse> getFollowing(String username) {
    Long userId = userService.getUserIdByUsername(username);
    return followRepo.findAllByFollowerId(userId).stream().map(follow->
    UserDtoResponse.toDtoResponse(follow.getFollowing())).toList();
    }

    public Boolean isFollowing(Long followingId, Long followerId) {
        return followRepo.existsByFollowingIdAndFollowerId(followingId, followerId);
    }
}
