package com.job.lk.webapp.controller;

import com.job.lk.webapp.service.impl.DialogflowService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;
import java.util.UUID;

@RestController
@RequestMapping("/api/dialogflow")
@RequiredArgsConstructor
public class DialogflowController {

    private final DialogflowService dialogflowService;

    @PostMapping("/query")
    public Map<String, String> detectIntent(@RequestBody Map<String, Object> request) {
        String sessionId = request.getOrDefault("sessionId", UUID.randomUUID().toString()).toString();
        String message = request.get("message").toString();

        // Extract jobId if present in parameters
        Long jobId = null;
        if (request.containsKey("parameters")) {
            Map<String, String> parameters = (Map<String, String>) request.get("parameters");
            if (parameters.containsKey("jobId")) {
                try {
                    jobId = Long.parseLong(parameters.get("jobId"));
                } catch (NumberFormatException e) {
                    // Handle invalid jobId format
                }
            }
        }
        String response = dialogflowService.detectIntent(sessionId, message, jobId);
        return Map.of("response", response);
    }
}
