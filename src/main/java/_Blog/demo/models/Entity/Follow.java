package _Blog.demo.models.Entity;

import java.time.LocalDateTime;
import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "follows", uniqueConstraints = @UniqueConstraint(columnNames = { "following_id", "follower_id" }))
@Data
@NoArgsConstructor
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

    @Column(updatable = false)
    private LocalDateTime createdAt = LocalDateTime.now();
}