package _Blog.demo.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import _Blog.demo.models.Entity.User;

public interface UserRepo extends JpaRepository<User, Long> {
    public Optional<User> findByusername(String username);

    public boolean existsByusername(String username);

    public boolean existsByemail(String email);
}