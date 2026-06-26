package com.example.dashboard.service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.PageRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.client.RestTemplate;

import com.example.dashboard.domain.Incident;
import com.example.dashboard.domain.MonitoringResult;
import com.example.dashboard.domain.MonitoringTarget;
import com.example.dashboard.repository.IncidentRepository;
import com.example.dashboard.repository.MonitoringResultRepository;
import com.example.dashboard.repository.MonitoringTargetRepository;

@Service
public class MonitoringService {

    private final IncidentRepository incidentRepository;
    private final MonitoringResultRepository monitoringResultRepository;
    private final MonitoringTargetRepository monitoringTargetRepository;
    private final RestTemplate restTemplate;

    public MonitoringService(IncidentRepository incidentRepository,
            MonitoringResultRepository monitoringResultRepository,
            MonitoringTargetRepository monitoringTargetRepository, RestTemplate restTemplate) {
        this.incidentRepository = incidentRepository;
        this.monitoringResultRepository = monitoringResultRepository;
        this.monitoringTargetRepository = monitoringTargetRepository;
        this.restTemplate = restTemplate;
    }

    @Transactional
    @Scheduled(fixedDelay = 60000)
    public void runCheck() {
        for (MonitoringTarget target : monitoringTargetRepository.findAll()) {
            long start = System.currentTimeMillis();
            try {
                ResponseEntity<String> response = restTemplate.getForEntity(target.getUrl(), String.class);
                long responseTime = System.currentTimeMillis() - start;

                MonitoringResult result = new MonitoringResult();
                result.setTargetId(target.getId());
                result.setSuccess(true);
                result.setStatusCode(response.getStatusCode().value());
                result.setResponseTime((int) responseTime);
                result.setCheckedAt(LocalDateTime.now());
                monitoringResultRepository.save(result);

                if (target.getStatus().equals("DOWN")) {
                    Optional<Incident> activeIncident = incidentRepository
                            .findByTargetIdAndRecoveredAtIsNull(target.getId());
                    activeIncident.ifPresent(incident -> {
                        incidentRepository.updateRecoveredAt(incident.getId(), LocalDateTime.now());
                    });
                    monitoringTargetRepository.updateStatus(target.getId(), "UP");
                } else if (target.getStatus().equals("UNKNOWN")) {
                    monitoringTargetRepository.updateStatus(target.getId(), "UP");
                }

            } catch (Exception e) {
                System.out.println("catchに入った理由: " + e.getClass().getName() + " - " + e.getMessage());

                long responseTime = System.currentTimeMillis() - start;
                MonitoringResult result = new MonitoringResult();
                result.setTargetId(target.getId());
                result.setSuccess(false);
                result.setStatusCode(null);
                result.setResponseTime((int) responseTime);
                result.setCheckedAt(LocalDateTime.now());
                monitoringResultRepository.save(result);

                List<MonitoringResult> recentResults = monitoringResultRepository.findByTargetIdOrderByCheckedAtDesc(
                        result.getTargetId(), PageRequest.of(0, target.getFailureThreshold()));
                if (recentResults.size() == target.getFailureThreshold()
                        && recentResults.stream().allMatch(r -> !r.getSuccess())) {

                    // すでに発生中の障害がなければ新しく作る
                    boolean alreadyDown = incidentRepository
                            .findByTargetIdAndRecoveredAtIsNull(target.getId())
                            .isPresent();

                    if (!alreadyDown) {
                        Incident incident = new Incident();
                        incident.setTarget(target);
                        incident.setFailureType("NO_RESPONSE");
                        incident.setOccurredAt(LocalDateTime.now());
                        incidentRepository.save(incident);

                        monitoringTargetRepository.updateStatus(target.getId(), "DOWN");
                    }
                }
            }
        }
    }
}
