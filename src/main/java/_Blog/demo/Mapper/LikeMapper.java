package _Blog.demo.Mapper;

import _Blog.demo.models.Entity.Like;
import _Blog.demo.models.Entity.Post;
import _Blog.demo.models.Entity.User;

public class LikeMapper {
    public static Like toEntity(Post post, User user) {
        return Like.builder().post(post).user(user).build();
    } 
}
