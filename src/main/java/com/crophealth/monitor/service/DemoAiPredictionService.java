package com.crophealth.monitor.service;

import com.crophealth.monitor.dto.PredictionResult;
import org.springframework.stereotype.Service;
import java.io.File;
import java.util.Random;

@Service
public class DemoAiPredictionService implements AiPredictionService {
    
    private final Random random = new Random();

    @Override
    public PredictionResult analyzeCropImage(String cropName, File imageFile) {
        try { Thread.sleep(2000); } catch (InterruptedException e) {}
        
        double confidence = 85.0 + (random.nextDouble() * 14.9);
        confidence = Math.round(confidence * 10.0) / 10.0;
        
        boolean isHealthy = random.nextBoolean();
        
        if (isHealthy) {
            return new PredictionResult("None", "Healthy", confidence, 
                "No visible symptoms. Plant appears vigorous.", 
                "Continue regular maintenance and watering.", 
                "Maintain good spacing and airflow.");
        } else {
            return getDiseaseInfo(cropName, confidence);
        }
    }
    
    private PredictionResult getDiseaseInfo(String cropName, double confidence) {
        switch (cropName.toLowerCase()) {
            case "tomato":
                if (random.nextBoolean()) {
                    return new PredictionResult("Early Blight", "Diseased", confidence, "Dark concentric spots on older leaves.", "Apply copper-based fungicide.", "Crop rotation, avoid overhead watering.");
                } else {
                    return new PredictionResult("Late Blight", "Diseased", confidence, "Large, dark, water-soaked spots on leaves and stems.", "Apply chlorothalonil or mancozeb.", "Destroy infected plants, plant resistant varieties.");
                }
            case "potato":
                return new PredictionResult("Late Blight", "Diseased", confidence, "Dark lesions on leaves, white fungal growth in high humidity.", "Fungicide application.", "Use certified disease-free seed tubers.");
            case "corn":
                return new PredictionResult("Common Rust", "Diseased", confidence, "Rust-colored pustules on both surfaces of leaves.", "Foliar fungicides if severe.", "Plant resistant hybrids.");
            case "apple":
                return new PredictionResult("Apple Scab", "Diseased", confidence, "Olive-green to black spots on leaves and fruit.", "Apply appropriate fungicides during early growth.", "Rake and destroy fallen leaves.");
            case "grape":
                return new PredictionResult("Black Rot", "Diseased", confidence, "Brown circular lesions on leaves, berries shrivel and turn black.", "Apply protectant fungicides.", "Canopy management for air circulation, remove mummies.");
            default:
                return new PredictionResult("Unknown Fungal Infection", "Diseased", confidence, "Discoloration or spots on leaves.", "Consult local extension office.", "Improve air circulation and avoid overhead watering.");
        }
    }
}
