    package _Blog.demo.repository;


import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
    import org.springframework.stereotype.Repository;

    import _Blog.demo.models.Entity.Follow;

    @Repository
    public interface FollowRepo extends JpaRepository<Follow, Long> {
        public boolean existsByFollowingIdAndFollowerId(Long followingId, Long followerId);
        public Page<Follow> findAllByFollowerId(Long followerId, Pageable pageable);
        public Page<Follow> findAllByFollowingId(Long followingId, Pageable pageable);
        public void deleteByFollowingIdAndFollowerId(Long followingId, Long followerId);
    }
