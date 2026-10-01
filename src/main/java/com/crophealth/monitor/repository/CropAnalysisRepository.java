package com.crophealth.monitor.repository;

import com.crophealth.monitor.entity.CropAnalysis;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;
import java.util.Optional;

public interface CropAnalysisRepository extends JpaRepository<CropAnalysis, Long> {
    List<CropAnalysis> findByUserIdOrderByAnalyzedAtDesc(Long userId);
    Optional<CropAnalysis> findByIdAndUserId(Long id, Long userId);
    long countByUserId(Long userId);
    long countByUserIdAndHealthStatus(Long userId, String healthStatus);
}
