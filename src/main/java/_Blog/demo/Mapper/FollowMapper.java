package _Blog.demo.Mapper;

import _Blog.demo.models.Entity.Follow;
import _Blog.demo.models.Entity.User;

public class FollowMapper {
        static public Follow toFollowEntity(User followingUser, User followerUser) {
        return Follow.builder().following(followingUser).follower(followerUser).build();
    }
}
