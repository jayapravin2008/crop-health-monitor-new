package com.crophealth.monitor.controller;

import com.crophealth.monitor.entity.User;
import com.crophealth.monitor.service.CropAnalysisService;
import com.crophealth.monitor.service.UserService;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
public class ProfileController {
    
    @Autowired
    private UserService userService;
    
    @Autowired
    private CropAnalysisService analysisService;
    
    @GetMapping("/profile")
    public String profile(HttpSession session, Model model) {
        Long userId = (Long) session.getAttribute("userId");
        User user = userService.getUserById(userId);
        model.addAttribute("user", user);
        model.addAttribute("totalAnalyses", analysisService.getTotalAnalyses(userId));
        model.addAttribute("healthyCount", analysisService.getHealthyCount(userId));
        model.addAttribute("diseasedCount", analysisService.getDiseasedCount(userId));
        return "profile";
    }
    
    @PostMapping("/profile/update")
    public String updateProfile(@RequestParam("name") String name, 
                                @RequestParam(value="password", required=false) String password,
                                HttpSession session, Model model) {
        Long userId = (Long) session.getAttribute("userId");
        userService.updateName(userId, name);
        session.setAttribute("userName", name);
        if(password != null && !password.trim().isEmpty() && password.length() >= 6) {
            userService.updatePassword(userId, password);
        }
        return "redirect:/profile?success";
    }
}
