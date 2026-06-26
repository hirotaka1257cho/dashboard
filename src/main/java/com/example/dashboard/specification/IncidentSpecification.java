package com.example.dashboard.specification;

import java.time.LocalDateTime;

import org.springframework.data.jpa.domain.Specification;

import com.example.dashboard.domain.Incident;

public class IncidentSpecification {

    public static Specification<Incident> isActive(){
        return(root, query, cb) -> cb.isNull(root.get("recoveredAt"));
    }

    public static Specification<Incident> company(String company){
        return(root, query, cb) -> cb.like(root.get("target").get("company"), "%" + company + "%");
    }

    public static Specification<Incident> name(String name){
        return(root, query, cb) -> cb.like(root.get("target").get("name"), "%" + name + "%");
    }

    public static Specification<Incident> occurredAt(LocalDateTime dateTime){
        return(root, query, cb) -> cb.greaterThanOrEqualTo(root.get("occurredAt"), dateTime);
    }
}
