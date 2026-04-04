package _Blog.demo.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;
import org.springframework.transaction.annotation.Transactional;

import _Blog.demo.DTO.requests.ReportRequest;
import _Blog.demo.Mapper.ReportMapper;
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
        if (body.getReportingUserId() == 0 && body.getReason() == null && body.getReason().isEmpty()) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Please fill all requireds fiels");
        }
        System.out.println(body.toString());
        if (!userService.userExistsById(body.getReportingUserId()))
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Reporting UserId is invalid");

        User reporter = userService.getMe();
        User reporting = userService.getUserById(body.getReportingUserId());
        if (reporter.getId() == reporting.getId()) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "You can't reporting yourself");
        }
        if (isReportUserAlreadyExists(reporter, reporting)) {
            throw new ResponseStatusException(HttpStatus.CONFLICT, "You're already reports this user");
        }
        Report reportEntity = ReportMapper.toReportUserEntity(body, reporter, reporting);
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

        if (postService.isPostExists(body.getReportedPostId()))
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Post Id invalid");

        User reporter = userService.getMe();
        Post reportedPost = postService.getPostById(body.getReportedPostId());
        if (reporter.getId() == reportedPost.getId()) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "You can't reporting your post");
        }

        if (isReportPostAlreadyExists(reporter, reportedPost)) {
            throw new ResponseStatusException(HttpStatus.CONFLICT, "You're already reports this user");
        }

        Report reportEntity = ReportMapper.toReportPostEntity(body, reporter, reportedPost);
        if (reportEntity != null) {
            reportRepo.save(reportEntity);
        }
        throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR,
                "Something went wrong try again later");

    }

    @Transactional(readOnly = true)
    public boolean isReportUserAlreadyExists(User reporter, User reportingUser) {
        return reportRepo.existsByReporterIdAndReportingUserId(reporter.getId(), reportingUser.getId());
    }

    @Transactional(readOnly = true)
    public boolean isReportPostAlreadyExists(User reporter, Post reportedPost) {
        return reportRepo.existsByReporterIdAndReportingPostId(reporter.getId(), reportedPost.getId());
    }

    public List<Report> GetReportsByReporterId(Long userId) {
        if (!userService.userExistsById(userId))
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "User Id is invalid");
        List<Report> reports = reportRepo.findAllByReporterId(userId);
        return reports;
    }

    public List<Report> GetAllReports() {
        return reportRepo.findAll(Sort.by("createdAt").descending());
    }
}
