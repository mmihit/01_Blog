package _Blog.demo.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import _Blog.demo.models.Entity.Report;

@Repository
public interface ReportRepo extends JpaRepository<Report, Long> {
    public boolean existsByReporterIdAndReportingUserId(Long reporterId, Long reportingId);
    public boolean existsByReporterIdAndReportingPostId(Long reporterId, Long reportingId);
    public List<Report> findAllByReporterId(Long reportedId);
}
