package org.example.repository.comment.report;

import java.util.UUID;
import org.example.entity.comment.Report;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ReportRepository extends JpaRepository<Report, UUID> {}
