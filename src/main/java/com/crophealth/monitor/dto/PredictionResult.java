package com.crophealth.monitor.dto;

public class PredictionResult {
    private String diseaseName;
    private String healthStatus;
    private Double confidence;
    private String symptoms;
    private String treatment;
    private String prevention;

    public PredictionResult(String diseaseName, String healthStatus, Double confidence, String symptoms, String treatment, String prevention) {
        this.diseaseName = diseaseName;
        this.healthStatus = healthStatus;
        this.confidence = confidence;
        this.symptoms = symptoms;
        this.treatment = treatment;
        this.prevention = prevention;
    }

    public String getDiseaseName() { return diseaseName; }
    public String getHealthStatus() { return healthStatus; }
    public Double getConfidence() { return confidence; }
    public String getSymptoms() { return symptoms; }
    public String getTreatment() { return treatment; }
    public String getPrevention() { return prevention; }
}
