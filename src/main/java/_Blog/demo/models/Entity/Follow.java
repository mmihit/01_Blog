package _Blog.demo.models.Entity;

import java.time.LocalDateTime;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "follows", uniqueConstraints = @UniqueConstraint(columnNames = { "following_id", "follower_id" }))
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Follow {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @ManyToOne
    @JoinColumn(name = "following_id", nullable = false)
    private User following;

    @ManyToOne
    @JoinColumn(name = "follower_id", nullable = false)
    private User follower;

    @Builder.Default
    @Column(updatable = false)
    private LocalDateTime createdAt = LocalDateTime.now();

    static public Follow toFollowEntity(User followingUser, User followerUser) {
        return Follow.builder().following(followingUser).follower(followerUser).build();
    }
}