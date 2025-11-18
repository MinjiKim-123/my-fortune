package com.fortune.config;

import org.springframework.ai.chat.client.ChatClient;
import org.springframework.ai.vertexai.gemini.VertexAiGeminiChatModel;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class AiConfig {
	
	@Bean
	ChatClient geminiChatClient(VertexAiGeminiChatModel chatModel) {
		return ChatClient.create(chatModel);
	}

}
