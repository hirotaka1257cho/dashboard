package com.example.dashboard.repository;

import java.time.LocalDateTime;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.example.dashboard.domain.Incident;

public interface IncidentRepository extends JpaRepository<Incident, Long>, JpaSpecificationExecutor<Incident>{

    @Modifying
    @Query("UPDATE Incident i SET i.recoveredAt = :recoveredAt WHERE i.id = :id")
    void updateRecoveredAt(@Param("id") long id, @Param("recoveredAt") LocalDateTime recoveredAt);

    Optional<Incident> findByTargetIdAndRecoveredAtIsNull(long targetId);

    long countByRecoveredAtIsNull();
}
