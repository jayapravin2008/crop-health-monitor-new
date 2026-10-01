package com.crophealth.monitor.service;

import com.crophealth.monitor.dto.PredictionResult;
import com.crophealth.monitor.entity.CropAnalysis;
import com.crophealth.monitor.repository.CropAnalysisRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class CropAnalysisService {
    @Autowired
    private CropAnalysisRepository repository;
    
    public CropAnalysis saveAnalysis(Long userId, String cropName, String imagePath, PredictionResult result) {
        CropAnalysis analysis = new CropAnalysis();
        analysis.setUserId(userId);
        analysis.setCropName(cropName);
        analysis.setImagePath(imagePath);
        analysis.setDiseaseName(result.getDiseaseName());
        analysis.setHealthStatus(result.getHealthStatus());
        analysis.setConfidence(result.getConfidence());
        analysis.setSymptoms(result.getSymptoms());
        analysis.setTreatment(result.getTreatment());
        analysis.setPrevention(result.getPrevention());
        return repository.save(analysis);
    }
    
    public List<CropAnalysis> getUserAnalyses(Long userId) {
        return repository.findByUserIdOrderByAnalyzedAtDesc(userId);
    }
    
    public CropAnalysis getAnalysisByIdAndUserId(Long id, Long userId) {
        return repository.findByIdAndUserId(id, userId).orElse(null);
    }
    
    public void deleteAnalysis(Long id) {
        repository.deleteById(id);
    }
    
    public long getTotalAnalyses(Long userId) {
        return repository.countByUserId(userId);
    }
    
    public long getHealthyCount(Long userId) {
        return repository.countByUserIdAndHealthStatus(userId, "Healthy");
    }
    
    public long getDiseasedCount(Long userId) {
        return repository.countByUserIdAndHealthStatus(userId, "Diseased");
    }
}
