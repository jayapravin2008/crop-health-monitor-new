package com.crophealth.monitor.service;

import com.crophealth.monitor.dto.PredictionResult;
import java.io.File;

public interface AiPredictionService {
    PredictionResult analyzeCropImage(String cropName, File imageFile);
}
