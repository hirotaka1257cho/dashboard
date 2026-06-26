package com.example.dashboard.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.example.dashboard.domain.MonitoringTarget;
import com.example.dashboard.repository.MonitoringTargetRepository;

@Service
public class MonitoringTargetService {

    private final MonitoringTargetRepository monitoringTargetRepository;

    public MonitoringTargetService(MonitoringTargetRepository monitoringTargetRepository) {
        this.monitoringTargetRepository = monitoringTargetRepository;
    }

    public List<MonitoringTarget> findAll() {
        return monitoringTargetRepository.findAll();
    }

    public void save(MonitoringTarget target){
        monitoringTargetRepository.save(target);
    }
}
