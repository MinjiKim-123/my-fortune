package com.fortune.service;

import org.springframework.ai.chat.model.ChatResponse;
import org.springframework.ai.chat.prompt.Prompt;
import org.springframework.ai.vertexai.gemini.VertexAiGeminiChatModel;
import org.springframework.stereotype.Service;

import com.fortune.util.ChatUtil;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Service
public class GeminiChatService {

     private final VertexAiGeminiChatModel geminiChatModel;

     public String chat(String userMessage) {
        ChatResponse response = ChatUtil.call(geminiChatModel, userMessage);
        return ChatUtil.getResponseText(response);
    }
	
    public String chat(String userMessage, String systemMessage) {
        ChatResponse response = ChatUtil.call(geminiChatModel, userMessage, systemMessage);
        return ChatUtil.getResponseText(response);
    }
}
