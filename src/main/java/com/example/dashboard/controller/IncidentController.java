package com.example.dashboard.controller;

import java.time.LocalDateTime;

import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.example.dashboard.domain.Incident;
import com.example.dashboard.service.IncidentService;

@Controller
public class IncidentController {

    private final IncidentService incidentService;

    public IncidentController(IncidentService incidentService) {
        this.incidentService = incidentService;
    }

    @RequestMapping("/incidents")
    public String list(@RequestParam(required = false) String company,
            @RequestParam(required = false) String name,
            @RequestParam(required = false) String occurredAt,
            @RequestParam(required = false) Boolean activeOnly,
            @PageableDefault(size = 20, sort = "occurredAt", direction = Sort.Direction.DESC) Pageable pageable,
            Model model) {
        LocalDateTime dateTime = occurredAt != null && !occurredAt.isEmpty()
                ? LocalDateTime.parse(occurredAt + "T00:00:00")
                : null;
        Page<Incident> page = incidentService.search(company, name, dateTime, activeOnly, pageable);
        model.addAttribute("page", page);
        model.addAttribute("incidents", page.getContent());
        return "incidents/list";
    }

}
