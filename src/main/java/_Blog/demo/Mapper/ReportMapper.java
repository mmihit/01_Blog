package _Blog.demo.Mapper;

import java.util.ArrayList;
import java.util.List;

import _Blog.demo.DTO.requests.ReportRequest;
import _Blog.demo.DTO.responses.ReportDtoResponse;
import _Blog.demo.DTO.responses.ReportLiteDtoResponse;
import _Blog.demo.DTO.responses.UserLiteDtoResponse;
import _Blog.demo.models.Entity.Post;
import _Blog.demo.models.Entity.Report;
import _Blog.demo.models.Entity.User;
import _Blog.demo.types.ReportType;

public class ReportMapper {
    static public Report toReportUserEntity(ReportRequest requestDto, User reporter, User reportingUser) {
        return Report.builder()
                .reporter(reporter)
                .reportingUser(reportingUser)
                .body(requestDto.getReason())
                .reportType(ReportType.USER_PROFILE)
                .build();
    }

    static public Report toReportPostEntity(ReportRequest requestDto, User reporter, Post reportedPost) {
        return Report.builder()
                .reporter(reporter)
                .reportingPost(reportedPost)
                .body(requestDto.getReason())
                .reportType(ReportType.POST_CONTENT)
                .build();
    }

    static public ReportDtoResponse toReportDtoResponse(Report report) {
                if (report == null) {
            return null;
        }
        return ReportDtoResponse.builder()
                .id(report.getId())
                .reporter(UserMapper.toUserLiteDtoResponse(report.getReporter()))
                .reportType(report.getReportType())
                .reportingUser(UserMapper.toUserLiteDtoResponse(report.getReportingUser()))
                .reportingPost(PostMapper.toPostLiteDtoResponse(report.getReportingPost(), new UserLiteDtoResponse()))
                .body(report.getBody())
                .status(report.getStatus())
                .createdAt(report.getCreatedAt())
                .build();
    }

    static public List<ReportDtoResponse> toReportsDtoResponse(List<Report> reports) {
        if (reports == null || reports.size() == 0) {
            return new ArrayList<>();
        }
        return reports.stream()
                .map(report -> ReportMapper.toReportDtoResponse(report))
                .toList();
    }

    static public ReportLiteDtoResponse toReportLiteDtoResponse(Report report) {
        if (report == null) {
            return null;
        }
        return ReportLiteDtoResponse.builder().id(report.getId())
                .reporter(UserMapper.toUserLiteDtoResponse(report.getReporter()))
                .reportingUser(UserMapper.toUserLiteDtoResponse(report.getReportingUser()))
                .reportingPost(PostMapper.toPostLiteDtoResponse(report.getReportingPost(), null))
                .reportType(report.getReportType())
                .build();
    }

    static public List<ReportLiteDtoResponse> toReportsLiteDtoResponse(List<Report> reports) {
        if (reports == null || reports.size() == 0) {
            return new ArrayList<>();
        }
        return reports.stream()
                .map(report -> ReportMapper.toReportLiteDtoResponse(report))
                .toList();
    }
}
