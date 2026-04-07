package _Blog.demo.DTO.responses;

import java.time.LocalDateTime;

import _Blog.demo.types.ReportType;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class ReportDtoResponse {
    protected long id;
    protected UserLiteDtoResponse reporter;
    protected ReportType reportType;
    protected UserLiteDtoResponse reportingUser;
    protected PostLiteDtoResponse reportingPost;
    protected String body;
    protected String status;
    protected LocalDateTime createdAt;
}
