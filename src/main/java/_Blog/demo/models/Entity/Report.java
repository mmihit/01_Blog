package _Blog.demo.models.Entity;

import java.time.LocalDateTime;

import _Blog.demo.types.ReportType;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "reports")
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Report {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "reporter_id", nullable = false)
    private User reporter;

    @ManyToOne
    @JoinColumn(name = "target_user_id", nullable = true)
    private User reportingUser;

    @ManyToOne
    @JoinColumn(name = "target_post_id", nullable = true)
    private Post reportingPost;

    @Column
    private String body;

    @Column
    private final String status = "pending";

    @Enumerated(EnumType.STRING)
    @Column(name = "report_type", nullable = false)
    private ReportType reportType;

    @Column(name = "created_at")
    private final LocalDateTime createdAt = LocalDateTime.now();
}