package _Blog.demo.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import _Blog.demo.models.Entity.Comment;

@Repository
public interface CommentRepo extends JpaRepository<Comment, Long> {
    public Page<Comment> findAllByPostId(Long id, Pageable pageable);
}
