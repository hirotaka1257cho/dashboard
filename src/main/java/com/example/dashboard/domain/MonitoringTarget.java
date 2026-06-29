package com.example.dashboard.domain;

import java.time.LocalDateTime;

import org.hibernate.validator.constraints.URL;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;

@Entity
@Table(name = "monitoring_targets")
public class MonitoringTarget {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @NotBlank(message = "必須項目です")
    private String name;
    @NotBlank(message = "必須項目です")
    private String company;
    @URL(message = "正しいURL形式で入力してください")
    @NotBlank(message = "URLを入力してください")
    private String url;
    @Min(value = 1, message = "1以上を指定してください")
    private int checkInterval;
    @Min(value = 1, message = "1以上を指定してください")
    private int failureThreshold;
    private String status;
    private LocalDateTime createdAt;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCompany() {
        return company;
    }

    public void setCompany(String company) {
        this.company = company;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public int getCheckInterval() {
        return checkInterval;
    }

    public void setCheckInterval(int checkInterval) {
        this.checkInterval = checkInterval;
    }

    public int getFailureThreshold() {
        return failureThreshold;
    }

    public void setFailureThreshold(int failureThreshold) {
        this.failureThreshold = failureThreshold;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }

    @Override
    public String toString() {
        return "MonitoringTarget [id=" + id + ", name=" + name + ", company=" + company + ", url=" + url
                + ", checkInterval=" + checkInterval + ", failureThreshold=" + failureThreshold + ", status=" + status
                + ", createdAt=" + createdAt + "]";
    }

}
