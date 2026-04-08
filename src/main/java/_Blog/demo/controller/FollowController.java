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

import _Blog.demo.DTO.requests.FollowRequest;
import _Blog.demo.DTO.responses.UserLiteDtoResponse;
import _Blog.demo.Mapper.FollowMapper;
import _Blog.demo.Mapper.PageMapper;
import _Blog.demo.Mapper.UserMapper;
import _Blog.demo.models.Entity.Follow;
import _Blog.demo.models.Entity.User;
import _Blog.demo.service.FollowService;

@RestController
@RequestMapping("/api/follow")
public class FollowController {
    @Autowired
    private FollowService followService;
    // @Autowired
    // private UserService userService;

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
    public ResponseEntity<Object> getFollowers(@PathVariable long id,
            @RequestParam(required = false, defaultValue = "1") int nOPage,
            @RequestParam(required = false, defaultValue = "10") int pageSize) {

        Pageable pageable = PageRequest.of(nOPage - 1, pageSize, Sort.by("createdAt").ascending());
        Page<Follow> followers = followService.getFollowers(id, pageable);
        List<User> users = FollowMapper.extractFollowers(followers.getContent());
        List<UserLiteDtoResponse> usersLite = UserMapper.toUsersLiteDtoResponse(users);

        return ResponseEntity.ok(PageMapper.toPageDtoResponse(usersLite, followers.hasNext()));
    }

    @GetMapping("/getFollowings/{id}")
    public ResponseEntity<Object> getFollowing(@PathVariable Long id,
            @RequestParam(required = false, defaultValue = "1") int nOPage,
            @RequestParam(required = false, defaultValue = "10") int pageSize) {

        Pageable pageable = PageRequest.of(nOPage - 1, pageSize, Sort.by("createdAt").ascending());
        Page<Follow> followings = followService.getFollowings(id, pageable);
        List<User> users = FollowMapper.extractFollowings(followings.getContent());
        List<UserLiteDtoResponse> userLite = UserMapper.toUsersLiteDtoResponse(users);
        return ResponseEntity.ok(PageMapper.toPageDtoResponse(userLite, followings.hasNext()));
    }
}
