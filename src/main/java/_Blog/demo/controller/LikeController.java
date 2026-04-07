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

import _Blog.demo.DTO.requests.LikeRequest;
import _Blog.demo.DTO.responses.UserDtoResponse;
import _Blog.demo.DTO.responses.UserLiteDtoResponse;
import _Blog.demo.Mapper.PageMapper;
import _Blog.demo.Mapper.UserMapper;
import _Blog.demo.models.Entity.User;
import _Blog.demo.service.LikeService;

@RestController
@RequestMapping("api/like")
public class LikeController {
    @Autowired
    private LikeService likeService;

    @PostMapping
    public ResponseEntity<Object> likeAction(@RequestBody LikeRequest likeRequest) {
        switch (likeRequest.getAction()) {
            case LIKE:
                likeService.AddLike(likeRequest.getPostId());
                break;
            case UNLIKE:
                likeService.DeleteLike(likeRequest.getPostId());
                break;
            default:
                return ResponseEntity.badRequest().body("Invalid like action");
        }
        return ResponseEntity.ok().body(null);
    }

    @GetMapping("getByPostId/{id}")
    public ResponseEntity<Object> getByPostId(@PathVariable Long id,
            @RequestParam (required = false, defaultValue = "1") int nOpage,
            @RequestParam (required = false, defaultValue = "5") int pageSize) {
                Pageable pageable = PageRequest.of(nOpage-1, pageSize, Sort.by("createdAt").ascending());
                Page<User> usersPage = likeService.GetLikersByPostId(id, pageable);
                List<UserLiteDtoResponse> usersLiteResponse = UserMapper.toUsersLiteDtoResponse(usersPage.getContent());

                return ResponseEntity.ok(PageMapper.toPageDtoResponse(usersLiteResponse,usersPage.hasNext()));
            }
}
