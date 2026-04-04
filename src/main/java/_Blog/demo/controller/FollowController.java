package _Blog.demo.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import _Blog.demo.DTO.requests.FollowRequest;
import _Blog.demo.DTO.responses.UserDtoResponse;
import _Blog.demo.DTO.responses.UserLiteDtoResponse;
import _Blog.demo.Mapper.UserMapper;
import _Blog.demo.models.Entity.User;
import _Blog.demo.service.FollowService;
import _Blog.demo.service.UserService;

@RestController
@RequestMapping("/api/follow")
public class FollowController {
    @Autowired
    private FollowService followService;
    @Autowired
    private UserService userService;

    @PostMapping
    public ResponseEntity<Object> followAction(@RequestBody FollowRequest body) {
        if (body.getAction().equals("follow")) {
            followService.followingUser(body.getFollowingUserId());
            return ResponseEntity.ok("You are now following this user");
        } else if (body.getAction().equals("unfollow")) {
            followService.unfollowingUser(body.getFollowingUserId());
            return ResponseEntity.ok("You have unfollowed this user");
        }
        return ResponseEntity.badRequest().body("Follow action is not valid");

    }

    @GetMapping("/getFollowers/{id}")
    public ResponseEntity<List<UserLiteDtoResponse>> getFollowers(@PathVariable long id) {
        List<User> followers = followService.getFollowers(id);
        return ResponseEntity.ok(UserMapper.toUsersLiteDtoResponse(followers));
    }

    @GetMapping("/getFollowings/{id}")
    public ResponseEntity<List<UserLiteDtoResponse>> getFollowing(@PathVariable Long id) {
        List<User> followings = followService.getFollowing(id);
        return ResponseEntity.ok(UserMapper.toUsersLiteDtoResponse(followings));
    }
}
