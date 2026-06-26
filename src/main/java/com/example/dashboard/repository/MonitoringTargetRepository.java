package com.example.dashboard.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.example.dashboard.domain.MonitoringTarget;

public interface MonitoringTargetRepository extends JpaRepository<MonitoringTarget, Long>{

    @Modifying
    @Query("UPDATE MonitoringTarget t SET t.status = :status WHERE t.id = :id")
    void updateStatus(@Param("id") long id, @Param("status") String status);
}
