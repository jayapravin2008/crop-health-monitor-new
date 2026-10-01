package com.crophealth.monitor;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import java.io.File;

@SpringBootApplication
public class CropHealthMonitorApplication {
    public static void main(String[] args) {
        new File("uploads").mkdirs();
        SpringApplication.run(CropHealthMonitorApplication.class, args);
    }
}
