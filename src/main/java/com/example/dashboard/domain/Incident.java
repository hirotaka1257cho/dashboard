package com.example.dashboard.domain;

import java.time.LocalDateTime;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

@Entity
@Table(name = "Incidents")
public class Incident {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    
    @ManyToOne
    @JoinColumn(name = "target_id")
    private MonitoringTarget target;

    private LocalDateTime occurredAt;
    private LocalDateTime recoveredAt;
    private String failureType;
    
    public long getId() {
        return id;
    }
    public void setId(long id) {
        this.id = id;
    }
    public MonitoringTarget getTarget() {
        return target;
    }
    public void setTarget(MonitoringTarget target) {
        this.target = target;
    }
    public LocalDateTime getOccurredAt() {
        return occurredAt;
    }
    public void setOccurredAt(LocalDateTime occurredAt) {
        this.occurredAt = occurredAt;
    }
    public LocalDateTime getRecoveredAt() {
        return recoveredAt;
    }
    public void setRecoveredAt(LocalDateTime recoveredAt) {
        this.recoveredAt = recoveredAt;
    }
    public String getFailureType() {
        return failureType;
    }
    public void setFailureType(String failureType) {
        this.failureType = failureType;
    }
    @Override
    public String toString() {
        return "Incident [id=" + id + ", target=" + target + ", occurredAt=" + occurredAt + ", recoveredAt="
                + recoveredAt + ", failureType=" + failureType + "]";
    }

    

}
