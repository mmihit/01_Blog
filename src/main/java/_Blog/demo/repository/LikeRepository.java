package _Blog.demo.repository;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import _Blog.demo.models.Entity.Like;
import _Blog.demo.models.Entity.User;

public interface LikeRepository extends JpaRepository<Like, Long> {
    public void deleteByPostIdAndUserId(Long postId, Long UserId);

    public Long countByPostId(Long postId);

    @Query("SELECT l.user FROM Like l WHERE l.post.id = :postId")
    public Page<User> findAllUsersByPostId(Long postId, Pageable pageable);
}
