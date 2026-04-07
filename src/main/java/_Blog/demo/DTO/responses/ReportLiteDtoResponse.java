package _Blog.demo.DTO.responses;

import _Blog.demo.types.ReportType;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class ReportLiteDtoResponse {
    private Long id;
    private UserLiteDtoResponse reporter;
    private UserLiteDtoResponse reportingUser;
    private PostLiteDtoResponse reportingPost;
    private ReportType reportType;
}
