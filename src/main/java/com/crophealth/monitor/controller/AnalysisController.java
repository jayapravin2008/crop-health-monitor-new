package com.crophealth.monitor.controller;

import com.crophealth.monitor.dto.PredictionResult;
import com.crophealth.monitor.entity.CropAnalysis;
import com.crophealth.monitor.service.AiPredictionService;
import com.crophealth.monitor.service.CropAnalysisService;
import com.crophealth.monitor.service.FileStorageService;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import java.io.File;

@Controller
public class AnalysisController {

    @Autowired
    private FileStorageService fileStorageService;
    
    @Autowired
    private AiPredictionService aiPredictionService;
    
    @Autowired
    private CropAnalysisService cropAnalysisService;

    @GetMapping("/analyze")
    public String analyzeForm() {
        return "analyze";
    }

    @PostMapping("/analyze")
    public String analyzeSubmit(@RequestParam("crop") String crop, 
                                @RequestParam("image") MultipartFile image,
                                HttpSession session, Model model) {
        Long userId = (Long) session.getAttribute("userId");
        try {
            if (image.isEmpty()) throw new Exception("Please select a valid image.");
            
            String contentType = image.getContentType();
            if(contentType == null || (!contentType.equals("image/jpeg") && !contentType.equals("image/png"))) {
                throw new Exception("Only JPG, JPEG, and PNG images are allowed.");
            }
            if(image.getSize() > 10 * 1024 * 1024) {
                throw new Exception("Image size must not exceed 10 MB.");
            }
            
            String filename = fileStorageService.storeFile(image);
            
            File imageFile = new File("uploads/" + filename);
            PredictionResult result = aiPredictionService.analyzeCropImage(crop, imageFile);
            
            CropAnalysis analysis = cropAnalysisService.saveAnalysis(userId, crop, filename, result);
            return "redirect:/analysis/" + analysis.getId();
            
        } catch (Exception e) {
            model.addAttribute("error", e.getMessage());
            return "analyze";
        }
    }
    
    @GetMapping("/analysis/{id}")
    public String viewResult(@PathVariable Long id, HttpSession session, Model model) {
        Long userId = (Long) session.getAttribute("userId");
        CropAnalysis analysis = cropAnalysisService.getAnalysisByIdAndUserId(id, userId);
        if(analysis == null) {
            return "redirect:/dashboard";
        }
        model.addAttribute("analysis", analysis);
        return "result";
    }
    
    @GetMapping("/history")
    public String history(HttpSession session, Model model) {
        Long userId = (Long) session.getAttribute("userId");
        model.addAttribute("analyses", cropAnalysisService.getUserAnalyses(userId));
        return "history";
    }
    
    @PostMapping("/analysis/delete/{id}")
    public String deleteAnalysis(@PathVariable Long id, HttpSession session) {
        Long userId = (Long) session.getAttribute("userId");
        CropAnalysis analysis = cropAnalysisService.getAnalysisByIdAndUserId(id, userId);
        if(analysis != null) {
            fileStorageService.deleteFile(analysis.getImagePath());
            cropAnalysisService.deleteAnalysis(id);
        }
        return "redirect:/history";
    }
}
