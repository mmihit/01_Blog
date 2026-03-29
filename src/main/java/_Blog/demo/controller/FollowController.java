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
        Long followingId = userService.getUserIdByUsername(body.getUsername());
        if (body.getAction().equals("follow")) {
            followService.followingUser(followingId);
            return ResponseEntity.ok("You are now following this user");
        } else if (body.getAction().equals("unfollow")) {
            followService.unfollowingUser(followingId);
            return ResponseEntity.ok("You have unfollowed this user");
        }
        return ResponseEntity.badRequest().body("Follow action is not valid");
    }

    @GetMapping("/getFollowers/{username}")
    public ResponseEntity<List<UserDtoResponse>> getFollowers(@PathVariable String username) {
        return ResponseEntity.ok(followService.getFollowers(username));
    }

    @GetMapping("/getFollowings/{username}")
    public ResponseEntity<List<UserDtoResponse>> getFollowing(@PathVariable String username) {
        return ResponseEntity.ok(followService.getFollowing(username));
    }
}
