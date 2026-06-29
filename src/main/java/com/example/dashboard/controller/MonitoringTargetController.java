package com.example.dashboard.controller;

import java.time.LocalDateTime;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import com.example.dashboard.domain.MonitoringTarget;
import com.example.dashboard.service.MonitoringTargetService;

import jakarta.validation.Valid;

@Controller
public class MonitoringTargetController {

    private final MonitoringTargetService monitoringTargetService;

    public MonitoringTargetController(MonitoringTargetService monitoringTargetService) {
        this.monitoringTargetService = monitoringTargetService;
    }

    @GetMapping("/targets")
    public String getMonitoringTargets(Model model) {
        model.addAttribute("targets", monitoringTargetService.findAll());
        return "targets/list";
    }

    @GetMapping("/targets/new")
    public String newForm(Model model) {
        model.addAttribute("target", new MonitoringTarget());
        return "targets/new";
    }

    @PostMapping("/targets")
    public String postMonitoringTargets(@Valid @ModelAttribute("target") MonitoringTarget target, BindingResult result) {
        if (result.hasErrors()) {
            System.out.println("エラー件数: " + result.getErrorCount());
            result.getAllErrors().forEach(e -> System.out.println(e.getDefaultMessage()));
            return "targets/new";
        }
        target.setStatus("UNKNOWN");
        target.setCreatedAt(LocalDateTime.now());
        monitoringTargetService.save(target);
        return "redirect:/targets";
    }
}
