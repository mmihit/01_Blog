package _Blog.demo.repository;


import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import _Blog.demo.models.Entity.Report;

@Repository
public interface ReportRepo extends JpaRepository<Report, Long> {
    public boolean existsByReporterIdAndReportingUserId(Long reporterId, Long reportingId);
    public boolean existsByReporterIdAndReportingPostId(Long reporterId, Long reportingId);
    public Page<Report> findAllByReporterId(Long reportedId, Pageable pageable);
    public Page<Report> findAll(Pageable pageable);
}
