package _Blog.demo.DTO.requests;

import _Blog.demo.types.ReportType;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class ReportRequest {
    private String reportingUsername;
    private Long reportedPostId;
    private ReportType reportType;
    private String reason;
}
