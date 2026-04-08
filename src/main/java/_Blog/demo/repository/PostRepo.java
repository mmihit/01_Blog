package _Blog.demo.repository;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import _Blog.demo.models.Entity.Post;

public interface PostRepo extends JpaRepository<Post, Long>{
    public Page<Post> findAllByUserIdAndStatus(Long id, String status, Pageable pageable);
}
