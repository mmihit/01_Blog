package _Blog.demo.Mapper;

import java.util.List;

import _Blog.demo.models.Entity.Follow;
import _Blog.demo.models.Entity.User;

public class FollowMapper {
    static public Follow toFollowEntity(User followingUser, User followerUser) {
        return Follow.builder().following(followingUser).follower(followerUser).build();
    }

    static public List<User> extractFollowers(List<Follow> follows) {
        return follows.stream()
                .map(follow -> follow.getFollower())
                .toList();
    }

    static public List<User> extractFollowings(List<Follow> follows) {
        return follows.stream()
                .map(follow -> follow.getFollowing())
                .toList();
    }
}
