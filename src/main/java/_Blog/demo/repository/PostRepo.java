package _Blog.demo.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import _Blog.demo.models.Entity.Post;

public interface PostRepo extends JpaRepository<Post, Long>{
    public List<Post> findAllByUserIdAndStatus(Long id, String status);
}
