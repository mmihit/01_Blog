package _Blog.demo.Mapper;

import java.util.List;

import _Blog.demo.DTO.requests.SignUpRequest;
import _Blog.demo.DTO.responses.UserDtoResponse;
import _Blog.demo.DTO.responses.UserLiteDtoResponse;
import _Blog.demo.models.Entity.Follow;
import _Blog.demo.models.Entity.User;

public class UserMapper {
    static public UserDtoResponse toDtoResponse(User user) {
        return UserDtoResponse.builder()
                .id(user.getId())
                .username(user.getUsername())
                .email(user.getEmail())
                .role(user.getRole())
                .avatar(user.getAvatar())
                .bio(user.getBio())
                .isBanned(user.isBanned())
                .createdAt(user.getCreatedAt())
                .build();
    }

    static public User toUserEntity(SignUpRequest userDto, String avatar) {
        return User.builder()
                .username(userDto.getUsername())
                .email(userDto.getEmail())
                .password(userDto.getPassword())
                .avatar(avatar)
                .bio(userDto.getBio())
                .build();
    }

    static public UserLiteDtoResponse toUserLiteDtoResponse(User user) {
        if (user == null)
            return null;
        return UserLiteDtoResponse.builder()
                .id(user.getId())
                .username(user.getUsername())
                .avatar(user.getAvatar())
                .build();
    }

    static public List<UserLiteDtoResponse> toUsersLiteDtoResponse(List<User> users) {
        return users.stream()
                .map(user -> UserMapper.toUserLiteDtoResponse(user))
                .toList();
    }


}
