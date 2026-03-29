package _Blog.demo.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import _Blog.demo.DTO.requests.ReportRequest;
import _Blog.demo.models.Entity.Report;
import _Blog.demo.service.ReportService;

@RestController
@RequestMapping("/api/report")
public class ReportController {

    @Autowired
    private ReportService reportService;

    @PostMapping("/send")
    public ResponseEntity<Object> SendReportToAdmin(@RequestBody ReportRequest body) {
        switch (body.getReportType()) {
            case POST_CONTENT:
                reportService.ReportingPostContent(body);
                break;
            case USER_PROFILE:
                reportService.ReportingUserProfile(body);
                break;
            default:
                return ResponseEntity.badRequest().body("Invalid report type");
        }
        return ResponseEntity.ok().body("You're report have send secceffully");
    }

    @GetMapping("/{username}")
    public ResponseEntity<Object> GetReportsByUsername(@PathVariable String username) {
        List<Report> reports = reportService.GetReportsByUsername(username);
        return ResponseEntity.ok("");
    }
}
