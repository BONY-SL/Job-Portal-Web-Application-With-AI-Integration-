package com.job.lk.webapp.service.impl;

import com.google.auth.oauth2.GoogleCredentials;
import com.google.cloud.dialogflow.v2.*;
import com.google.protobuf.Struct;
import com.job.lk.webapp.dto.CompanyDTO;
import com.job.lk.webapp.dto.JobDTO;
import com.job.lk.webapp.exception.coustom.DialogFlowError;
import com.job.lk.webapp.service.CompanyService;
import com.job.lk.webapp.service.JobService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Component;

import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;

@Component
@RequiredArgsConstructor
public class DialogflowService {

    @Value("${dialogflow.project-id}")
    private String projectId;

    @Value("${dialogflow.credentials-path}")
    private String filePath;

    private final JobService jobService;
    private final CompanyService companyService;

    public String detectIntent(String sessionId, String message, Long jobId) {
        try {
            // Load credentials
            ClassPathResource resource = new ClassPathResource(filePath);
            InputStream inputStream = resource.getInputStream();
            GoogleCredentials credentials = GoogleCredentials.fromStream(inputStream);

            // Create SessionsClient
            SessionsSettings.Builder settingsBuilder = SessionsSettings.newBuilder();
            SessionsSettings sessionsSettings = settingsBuilder.setCredentialsProvider(() -> credentials).build();
            SessionsClient sessionsClient = SessionsClient.create(sessionsSettings);

            // Create a session path
            SessionName session = SessionName.of(projectId, sessionId);

            // Build the query input
            TextInput.Builder textInput = TextInput.newBuilder()
                    .setText(message)
                    .setLanguageCode("en-US");
            QueryInput queryInput = QueryInput.newBuilder()
                    .setText(textInput)
                    .build();

            // Prepare parameters map
            Map<String, String> parameters = new HashMap<>();

            // If jobId is provided, fetch job and company details
            if (jobId != null) {
                JobDTO job = jobService.getJobById(jobId);
                if (job != null) {
                    parameters.put("job_title", job.getTitle());
                    parameters.put("skills_required", job.getSkillsRequired());
                    parameters.put("location", job.getLocation());
                    parameters.put("salary", job.getSalary());
                    // Add other job fields as needed

                    // Get company details
                    CompanyDTO company = companyService.getCompanyById(job.getCompanyId());
                    if (company != null) {
                        parameters.put("company", company.getName());
                        // Add other company fields as needed
                    }
                }
            }

            // Build parameters
            QueryParameters.Builder queryParams = QueryParameters.newBuilder();
            Struct.Builder structBuilder = Struct.newBuilder();

            // Add all parameters to the context
            parameters.forEach((key, value) -> structBuilder.putFields(key, com.google.protobuf.Value.newBuilder().setStringValue(value).build()));

            queryParams.setPayload(structBuilder.build());

            // Build the request
            DetectIntentRequest request = DetectIntentRequest.newBuilder()
                    .setSession(session.toString())
                    .setQueryInput(queryInput)
                    .setQueryParams(queryParams.build())
                    .build();

            // Execute the request
            DetectIntentResponse response = sessionsClient.detectIntent(request);
            String fulfillmentText = response.getQueryResult().getFulfillmentText();

            // Replace placeholders with actual values
            fulfillmentText = replacePlaceholders(fulfillmentText, parameters);

            return fulfillmentText;

        } catch (Exception e) {
            throw new DialogFlowError("Error communicating with Dialogflow: " + e.getMessage());
        }
    }

    private String replacePlaceholders(String text, Map<String, String> parameters) {
        for (Map.Entry<String, String> entry : parameters.entrySet()) {
            // Replace ${parameter} format
            String placeholder = "${" + entry.getKey() + "}";
            text = text.replace(placeholder, entry.getValue());
        }
        return text;
    }
}
