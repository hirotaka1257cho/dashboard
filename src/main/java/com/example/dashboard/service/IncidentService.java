package com.example.dashboard.service;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import com.example.dashboard.domain.Incident;
import com.example.dashboard.repository.IncidentRepository;
import com.example.dashboard.repository.MonitoringTargetRepository;
import com.example.dashboard.specification.IncidentSpecification;

@Service
public class IncidentService {

    private final IncidentRepository incidentRepository;
    private final MonitoringTargetRepository monitoringTargetRepository;

    public IncidentService(IncidentRepository incidentRepository,
            MonitoringTargetRepository monitoringTargetRepository) {
        this.incidentRepository = incidentRepository;
        this.monitoringTargetRepository = monitoringTargetRepository;
    }

    public List<Incident> findAll(){
        return incidentRepository.findAll();
    }

    public Page<Incident> search(String company, String name, LocalDateTime occuredAt, Boolean activeOnly, Pageable pageable){

        Specification<Incident> spec = (root, query, cb) -> cb.conjunction();
        if(activeOnly != null && activeOnly){
            spec = spec.and(IncidentSpecification.isActive());
        }
        if(company != null && !company.isEmpty()){
            spec = spec.and(IncidentSpecification.company(company));
        }
        if(name != null && !name.isEmpty()){
            spec = spec.and(IncidentSpecification.name(name));
        }
        if(occuredAt != null){
            spec = spec.and(IncidentSpecification.occurredAt(occuredAt));
        }

        return incidentRepository.findAll(spec, pageable);
    }

    public long countActive(){
        return incidentRepository.countByRecoveredAtIsNull();
    }

}
