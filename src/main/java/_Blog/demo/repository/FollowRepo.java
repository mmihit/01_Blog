    package _Blog.demo.repository;

    import java.util.List;

    import org.springframework.data.jpa.repository.JpaRepository;
    import org.springframework.stereotype.Repository;

    import _Blog.demo.models.Entity.Follow;

    @Repository
    public interface FollowRepo extends JpaRepository<Follow, Long> {
        public boolean existsByFollowingIdAndFollowerId(Long followingId, Long followerId);
        public List<Follow> findAllByFollowerId(Long followerId);
        public List<Follow> findAllByFollowingId(Long followingId);
        public void deleteByFollowingIdAndFollowerId(Long followingId, Long followerId);
    }
