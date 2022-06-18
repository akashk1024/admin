package com.greatlearning.microservice.repository;

import com.greatlearning.microservice.entity.AuditLog;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AuditLogRepository extends JpaRepository<AuditLog, Long> {
}
