package _Blog.demo.DTO.requests;

import lombok.Data;

@Data
public class FollowRequest {
    private String action;
    private String username;
}