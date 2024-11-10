package org.example.auth.dtos;

import java.util.List;

public class ChatMessageRequest {
    private List<Message> messages;
    private int max_tokens = 200;
    private double temperature = 0.7;
    private double frequency_penalty = 0.0;
    private double presence_penalty = 0.0;
    private double top_p = 0.95;

    public ChatMessageRequest() {}

    public List<Message> getMessages() {
        return messages;
    }

    public void setMessages(List<Message> messages) {
        this.messages = messages;
    }

    public int getMax_tokens() {
        return max_tokens;
    }

    public double getTemperature() {
        return temperature;
    }

    public double getFrequency_penalty() {
        return frequency_penalty;
    }

    public double getPresence_penalty() {
        return presence_penalty;
    }

    public double getTop_p() {
        return top_p;
    }

    public static class Message {
        private String role;
        private String content;

        public Message() {}

        public String getRole() {
            return role;
        }

        public void setRole(String role) {
            this.role = role;
        }

        public String getContent() {
            return content;
        }

        public void setContent(String content) {
            this.content = content;
        }
    }
}
