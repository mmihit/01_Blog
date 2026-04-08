package _Blog.demo.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import _Blog.demo.DTO.requests.ReportRequest;
import _Blog.demo.Mapper.PageMapper;
import _Blog.demo.Mapper.ReportMapper;
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

    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping("getAll")
    public ResponseEntity<Object> GetAllReports(
            @RequestParam(required = false, defaultValue = "1") int nOPage,
            @RequestParam(required = false, defaultValue = "10") int pageSize) {
                
        Pageable pageable = PageRequest.of(nOPage - 1, pageSize, Sort.by("createdAt").ascending());
        Page<Report> reports = reportService.GetAllReports(pageable);
        return ResponseEntity.ok(PageMapper
                .toPageDtoResponse(ReportMapper.toReportsLiteDtoResponse(reports.getContent()), reports.hasNext()));
    }

    @GetMapping("getByReporter/{id}")
    public ResponseEntity<Object> GetReportsByUserId(@PathVariable Long id,
            @RequestParam(required = false, defaultValue = "1") int nOPage,
            @RequestParam(required = false, defaultValue = "10") int pageSize) {

        Pageable pageable = PageRequest.of(nOPage - 1, pageSize, Sort.by("createdAt").ascending());
        Page<Report> reports = reportService.GetReportsByReporterId(id, pageable);
        return ResponseEntity.ok(PageMapper
                .toPageDtoResponse(ReportMapper.toReportsLiteDtoResponse(reports.getContent()), reports.hasNext()));
    }
}
