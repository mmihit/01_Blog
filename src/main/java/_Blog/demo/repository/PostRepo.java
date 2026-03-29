package _Blog.demo.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import _Blog.demo.models.Entity.Post;

public interface PostRepo extends JpaRepository<Post, Long>{

}
