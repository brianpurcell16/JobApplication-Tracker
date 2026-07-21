package com.jobtracker.service;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.jobtracker.dto.MatchResultResponse;
import com.jobtracker.model.Application;
import com.jobtracker.repository.ApplicationRepository;
import com.jobtracker.repository.ResumeProfileRepository;
import com.jobtracker.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import java.util.*;
import java.util.regex.MatchResult;



@Service
@RequiredArgsConstructor

public class MatchService {

    private final ApplicationRepository applicationRepository;
    private final RestTemplate restTemplate;
    private final ResumeService resumeService;
    private final ObjectMapper objectMapper = new ObjectMapper();

    @Value("${anthropic.api.key}")
    private String apiKey;

    @Value("${anthropic.api.base-url}")
    private String baseUrl;

    @Value("${anthropic.api.model}")
    private String model;

    public MatchResultReponse runMatch(Long userId, Long applicationId){
        Application application = applicationRepository.findById(applicationId).orElseThrow(() -> new IllegalStateException("No application found with id: " + applicationId));
        if(!application.getUser().getId().equals(userId)){
            throw new SecurityException("You are not allowed to run this application as you do not own it");
        }
        if(application.getJobDescription() == null || application.getJobDescription().isBlank()){
            throw new SecurityException("There is no job description available for this application");
        }

        String resumeText = resumeService.getResumeText(userId);
        String prompt = buildPrompt(resumeText, application.getJobDescription());
        JsonNode parsed = callClaude(prompt);

        int score = parsed.path("score").asInt(0);
        String summary = parsed.path("summary").asText("");
        List<String> strengths = toList(parsed.path("strengths"));
        List<String> gaps = toList(parsed.path("gaps"));

        application.setMatchScore(score);
        application.setMatchSummary(summary);
        application.setMatchStrengths(String.join("\n", strengths));
        application.setMatchGaps(String.join("\n", gaps));
        applicationRepository.save(application);

        return MatchResultResponse.builder()
                .score(score).summary(summary)
                .strengths(strengths).gaps(gaps)
                .build();

    }

    private String buildPrompt(String resumeText, String jobDescription) {
        return """
                You are an ATS and recruiting assistant. Compare the RESUME against the
                JOB DESCRIPTION and assess fit.
 
                Respond with ONLY a JSON object, no other text, no markdown fences:
                {
                  "score": <integer 0-100>,
                  "summary": "<one or two sentence overall assessment>",
                  "strengths": ["<short bullet>", "<short bullet>", "<short bullet>"],
                  "gaps": ["<short bullet>", "<short bullet>", "<short bullet>"]
                }
 
                RESUME:
                %s
 
                JOB DESCRIPTION:
                %s
                """.formatted(resumeText, jobDescription);
    }

    private JsonNode callClaude(String prompt) {
        HttpHeaders headers = new HttpHeaders();
        headers.set("x-api-key", apiKey);
        headers.set("anthropic-version","2023-06-01");
        headers.setContentType(MediaType.APPLICATION_JSON);

        Map<String, Object> body = new HashMap<>();
        body.put("model", model);
        body.put("max_tokens",1024);
        body.put("messages", List.of(Map.of("role", "user", "content", prompt)));

        HttpEntity<Map<String, Object>> request = new HttpEntity<>(body, headers);

        try{
            ResponseEntity<String> response = restTemplate.postForEntity(baseUrl, request, String.class);
            JsonNode root = objectMapper.readTree(response.getBody());
            String text = root.path("content").get(0).path("text").asText();
            return objectMapper.readTree(text);
        } catch (Exception e){
            throw new RuntimeException("AI match request failed: " +e.getMessage(),e);
        }
    }


    private List<String> toList(JsonNode arrayNode) {
        List<String> result = new ArrayList<>();
        if (arrayNode.isArray()) arrayNode.forEach(n -> result.add(n.asText()));
        return result;
    }


}
