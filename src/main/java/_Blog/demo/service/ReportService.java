package _Blog.demo.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;
import org.springframework.transaction.annotation.Transactional;

import _Blog.demo.DTO.requests.ReportRequest;
import _Blog.demo.models.Entity.Post;
import _Blog.demo.models.Entity.Report;
import _Blog.demo.models.Entity.User;
import _Blog.demo.repository.ReportRepo;

@Service
public class ReportService {

    @Autowired
    private ReportRepo reportRepo;

    @Autowired
    private UserService userService;

    @Autowired
    private PostService postService;

    @Transactional
    public void ReportingUserProfile(ReportRequest body) {
        if (body.getReportingUsername() == null && body.getReason() == null && body.getReason().isEmpty()) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Username or reason empty");
        }

        User reporter = userService.getMe();
        User reporting = userService.getUserByUsername(body.getReportingUsername());
        if (reporter.getId() == reporting.getId()) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "You can't reporting yourself");
        }
        if (isReportUserAlreadyExists(reporter, reporting)) {
            throw new ResponseStatusException(HttpStatus.CONFLICT, "You're already reports this user");
        }
        Report reportEntity = Report.toReportUserEntity(body, reporter, reporting);
        if (reportEntity != null) {
            reportRepo.save(reportEntity);
        } else {
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR,
                    "Something went wrong try again later");
        }

    }

    @Transactional
    public void ReportingPostContent(ReportRequest body) {
        if (body.getReportedPostId() == null && body.getReason() == null && body.getReason().isEmpty()) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Post or reason empty");
        }

        User reporter = userService.getMe();
        Post reportedPost = postService.getPostById(body.getReportedPostId());
        if (reporter.getId() == reportedPost.getId()) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "You can't reporting yourself");
        }
        if (isReportPostAlreadyExists(reporter, reportedPost)) {
            throw new ResponseStatusException(HttpStatus.CONFLICT, "You're already reports this user");
        }
        Report reportEntity = Report.toReportPostEntity(body, reporter, reportedPost);
        if (reportEntity != null) {
            reportRepo.save(reportEntity);
        } else {
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR,
                    "Something went wrong try again later");
        }
    }

    @Transactional(readOnly = true)
    public boolean isReportUserAlreadyExists(User reporter, User reportingUser) {
        return reportRepo.existsByReporterIdAndReportingUserId(reporter.getId(), reportingUser.getId());
    }

    @Transactional(readOnly = true)
    public boolean isReportPostAlreadyExists(User reporter, Post reportedPost) {
        return reportRepo.existsByReporterIdAndReportingPostId(reporter.getId(), reportedPost.getId());
    }

    public List<Report> GetReportsByUsername(String username) {
        Long userId = userService.getUserIdByUsername(username);
        List<Report> reports = reportRepo.findAllByReporterId(userId);
        return reports;
    }
}
