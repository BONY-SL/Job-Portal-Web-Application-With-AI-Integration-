package com.job.lk.webapp.service.impl;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.job.lk.webapp.dto.ResumeUrlDTO;
import org.apache.tika.exception.TikaException;
import org.apache.tika.metadata.Metadata;
import org.apache.tika.parser.AutoDetectParser;
import org.apache.tika.parser.ParseContext;
import org.apache.tika.sax.BodyContentHandler;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.xml.sax.SAXException;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URI;
import java.net.URL;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Component
public class ResumeParserService {


    @Value("${openai.api.key}")
    private String openApiKey;

    public  String parseResume(ResumeUrlDTO resumeUrlDTO) throws TikaException, IOException, SAXException, InterruptedException {
        String text = extractTextFromResume(resumeUrlDTO.getResumeUrl());
        return this.callOpenAPI(text);
    }

    private  String callOpenAPI(String resumeText) throws IOException, InterruptedException {

        // Convert text to JSON using Jackson to ensure a correct format
        ObjectMapper objectMapper = new ObjectMapper();
        Map<String, Object> requestMap = new HashMap<>();
        requestMap.put("model","gpt-4o-mini");

        List<Map<String,String>> messages = new ArrayList<>();
        Map<String ,String > message = new HashMap<>();
        message.put("role","user");
        message.put("content", "Extract skills and experience from this resume: (provide only skills separate by comma, dont provide any other unnecessary words) " + resumeText);
        messages.add(message);

        requestMap.put("messages", messages);

        // Convert Java Object to JSON string
        String requestBody = objectMapper.writeValueAsString(requestMap);
        HttpClient client = HttpClient.newHttpClient();
        HttpRequest httpRequest = HttpRequest.newBuilder()
                .uri(URI.create("https://api.openai.com/v1/chat/completions"))
                .header("Authorization", "Bearer " + this.openApiKey)
                .header("Content-Type", "application/json")
                .POST(HttpRequest.BodyPublishers.ofString(requestBody))
                .build();

        HttpResponse<String> response = client.send(httpRequest, HttpResponse.BodyHandlers.ofString());
        return response.body();
    }

    private  String extractTextFromResume(String resumeUrl) throws IOException, TikaException, SAXException {

        InputStream fileInputStream = downloadFile(resumeUrl);

        BodyContentHandler handler = new BodyContentHandler(-1);
        Metadata metaData = new Metadata();
        new AutoDetectParser().parse(fileInputStream,handler,metaData,new ParseContext());

        return handler.toString();

    }

    private  InputStream downloadFile(String fileUrl) throws IOException {
        URL url = new URL(fileUrl);
        HttpURLConnection connection = (HttpURLConnection) url.openConnection();

        connection.setRequestMethod("GET");
        connection.setConnectTimeout(5000);
        connection.setReadTimeout(5000);
        connection.connect();

        int responseCode = connection.getResponseCode();
        if (responseCode >= 200 && responseCode < 300) {
            return connection.getInputStream();
        } else {
            throw new IOException("Failed to download file: HTTP " + responseCode);
        }
    }

}
