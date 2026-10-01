package com.crophealth.monitor.controller;

import com.crophealth.monitor.entity.CropAnalysis;
import com.crophealth.monitor.service.CropAnalysisService;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import java.util.List;

@Controller
public class DashboardController {
    
    @Autowired
    private CropAnalysisService analysisService;

    @GetMapping("/dashboard")
    public String dashboard(HttpSession session, Model model) {
        Long userId = (Long) session.getAttribute("userId");
        long total = analysisService.getTotalAnalyses(userId);
        long healthy = analysisService.getHealthyCount(userId);
        long diseased = analysisService.getDiseasedCount(userId);
        
        double avgConfidence = 0;
        List<CropAnalysis> analyses = analysisService.getUserAnalyses(userId);
        if(!analyses.isEmpty()) {
            double sum = 0;
            for(CropAnalysis a : analyses) sum += a.getConfidence();
            avgConfidence = sum / analyses.size();
        }
        
        model.addAttribute("totalAnalyses", total);
        model.addAttribute("healthyCount", healthy);
        model.addAttribute("diseasedCount", diseased);
        model.addAttribute("avgConfidence", Math.round(avgConfidence * 10.0) / 10.0);
        
        model.addAttribute("recentAnalyses", analyses.stream().limit(5).toList());
        return "dashboard";
    }
}
