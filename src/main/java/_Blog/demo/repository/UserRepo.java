package _Blog.demo.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import _Blog.demo.models.User;

public interface UserRepo extends JpaRepository<User, Long> {
    public Optional<User> findbyusername(String username);
}