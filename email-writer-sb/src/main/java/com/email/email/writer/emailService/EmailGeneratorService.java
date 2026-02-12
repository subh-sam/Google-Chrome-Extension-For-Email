package com.email.email.writer.emailService;

import com.email.email.writer.EmailRequest;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import tools.jackson.core.JacksonException;
import tools.jackson.databind.JsonNode;
import tools.jackson.databind.ObjectMapper;

@Service
public class EmailGeneratorService {

    private final WebClient webClient;
    @Value("${gemini.api.key}")
    private String apiKey;

    @Value("${gemini.api.url}")
    private String baseUrl;

    public EmailGeneratorService(WebClient.Builder webClientBuilder) {
        this.webClient = webClientBuilder.build();
    }

    public String generateEmailReply(EmailRequest emailRequest) {
        // Build Prompt

        String prompt = buildPrompt(emailRequest);

        // Prepare raw JSON Body

        String requestBody = String.format("""
                {
                    "contents": [
                      {
                        "parts": [
                          {
                            "text": "%s"
                          }
                        ]
                      }
                    ]
                  }
                """,prompt);

        // Send Request

        String response = webClient.post()
                .uri(baseUrl)
                .header("Content-Type","application/json")
                .header("x-goog-api-key",apiKey)
                .bodyValue(requestBody)
                .retrieve()
                .bodyToMono(String.class)
                .block();

        // Extract Response

        return extractResponseContent(response);

    }

    private String extractResponseContent(String response) {
        try {
            ObjectMapper mapper = new ObjectMapper();
            JsonNode root = mapper.readTree(response);
            return root.path("candidates")
                    .get(0)
                    .path("content")
                    .path("parts")
                    .get(0)
                    .path("text")
                     .asString();
        } catch (JacksonException e) {
            throw new RuntimeException(e);
        }
    }

    private String buildPrompt(EmailRequest emailRequest) {

        StringBuilder prompt = new StringBuilder();

        prompt.append("""
    You are a professional workplace email assistant.

    Task:
    Write a clear, polite, and professional email reply.

    Guidelines:
    - Keep the response concise and well-structured
    - Do not include unnecessary explanations
    - Maintain a respectful and confident tone
    - Suitable for a corporate IT environment
    """);

        if (emailRequest.getTone() != null && !emailRequest.getTone().isBlank()) {
            prompt.append("Tone: ").append(emailRequest.getTone()).append("\n");
        } else {
            prompt.append("Tone: Professional\n");
        }

        prompt.append("""
    only give email in format manner do not give extra from your self
    Format:
    - Greeting
    - Subject line
    - Body (2–3 short paragraphs)
    - Polite closing

    Original Email:
    """);

        prompt.append(emailRequest.getEmailContent());

        return prompt.toString();
    }

}
