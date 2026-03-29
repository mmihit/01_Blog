package _Blog.demo.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import _Blog.demo.models.Entity.Media;

public interface MediaRepo extends JpaRepository<Media, Long>{
    public List<Media> findAllByPostId(Long postId);
}
