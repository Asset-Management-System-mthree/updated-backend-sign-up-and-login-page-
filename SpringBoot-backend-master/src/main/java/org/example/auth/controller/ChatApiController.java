package org.example.auth.controller;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.example.auth.dtos.ChatMessageRequest;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/api")
@CrossOrigin(origins = "http://localhost:4200")
public class ChatApiController {

    private final WebClient webClient;

    @Value("${openai.api.key}")
    private String apiKey;

    public ChatApiController(WebClient.Builder webClientBuilder) {
        this.webClient = webClientBuilder.baseUrl("https://chatbot-mthree-401.openai.azure.com").build();
    }

    @PostMapping("/custom-message")
    public Mono<String> getCustomMessage(@RequestBody ChatMessageRequest chatMessageRequest) {
        String urlPath = "/openai/deployments/gpt-35-turbo/chat/completions?api-version=2024-02-15-preview";

        return webClient.post()
                .uri(urlPath)
                .header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
                .header("api-key",apiKey)
                .bodyValue(chatMessageRequest)
                .retrieve()
                .bodyToMono(String.class)
                .map(this::extractMessageFromResponse);
    }

    private String extractMessageFromResponse(String responseBody) {
        try {
            ObjectMapper mapper = new ObjectMapper();
            JsonNode root = mapper.readTree(responseBody);
            return root.path("choices").get(0).path("message").path("content").asText();
        } catch (Exception e) {
            e.printStackTrace();
            return "Error retrieving message";
        }
    }
}