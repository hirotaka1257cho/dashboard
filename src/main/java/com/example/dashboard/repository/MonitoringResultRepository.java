package com.example.dashboard.repository;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.example.dashboard.domain.MonitoringResult;

public interface MonitoringResultRepository extends JpaRepository<MonitoringResult, Long>{

    List<MonitoringResult> findByTargetIdOrderByCheckedAtDesc(long targetId, Pageable pageable);
    
    @Modifying
    @Query("DELETE FROM MonitoringResult r WHERE r.checkedAt < :threshold")
    void deleteOlderThan(@Param("threshold") LocalDateTime threshold);

}
