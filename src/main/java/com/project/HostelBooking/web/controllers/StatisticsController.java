package com.project.HostelBooking.web.controllers;

import com.project.HostelBooking.services.StatisticsService;
import lombok.RequiredArgsConstructor;
import org.springframework.core.io.InputStreamResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.FileInputStream;
import java.io.IOException;

@RestController
@RequestMapping("/api/statistics")
@RequiredArgsConstructor
public class StatisticsController {

    private final StatisticsService statisticsService;

    @GetMapping
    public ResponseEntity<InputStreamResource> exportStatistics() throws IOException {
        String filePath = "statistics.csv";
        statisticsService.exportStatisticsToCsv(filePath);

        InputStreamResource resource = new InputStreamResource(new FileInputStream(filePath));
        HttpHeaders headers = new HttpHeaders();
        headers.add(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=statistics.csv");

        return ResponseEntity.ok()
                .headers(headers)
                .body(resource);
    }
}
