package _Blog.demo.Mapper;

import java.util.List;

import _Blog.demo.DTO.responses.CommentDtoResponse;
import _Blog.demo.models.Entity.Comment;
import _Blog.demo.models.Entity.Post;
import _Blog.demo.models.Entity.User;

public class CommentMapper {
    public static Comment toCommentEntity(String body, User user, Post post) {
        return Comment.builder()
                .user(user)
                .post(post)
                .content(body)
                .build();
    }

    public static CommentDtoResponse toCommentDtoResponse(Comment comment) {
        return CommentDtoResponse.builder()
                .id(comment.getId())
                .user(UserMapper.toUserLiteDtoResponse(comment.getUser()))
                .content(comment.getContent())
                .createdAt(comment.getCreatedAt())
                .build();
    }

    public static List<CommentDtoResponse> toCommentsDtoResponses(List<Comment> comments) {
        return comments.stream().map(comment -> toCommentDtoResponse(comment)).toList();
    }
}
