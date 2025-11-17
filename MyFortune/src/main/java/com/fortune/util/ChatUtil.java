package com.fortune.util;

import java.util.List;
import java.util.Optional;

import org.springframework.ai.chat.messages.AssistantMessage;
import org.springframework.ai.chat.messages.Message;
import org.springframework.ai.chat.messages.SystemMessage;
import org.springframework.ai.chat.messages.UserMessage;
import org.springframework.ai.chat.model.ChatModel;
import org.springframework.ai.chat.model.ChatResponse;
import org.springframework.ai.chat.model.Generation;
import org.springframework.ai.chat.prompt.Prompt;

/**
 * chat ai 호출용 유틸리티
 */
public class ChatUtil {
    
    /**
     * chat ai 호출
     * @param chatModel 호출할 chat 모델
     * @param userMessage 사용자 메세지
     * @return chat ai 응답 객체
     */
    public static ChatResponse call(ChatModel chatModel, String userMessage) {
        UserMessage message = new UserMessage(userMessage);
        Prompt chatPrompt = new Prompt(message);
        return chatModel.call(chatPrompt);
    }

        /**
     * chat ai 호출
     * @param chatModel 호출할 chat 모델
     * @param userMessage 사용자 메세지
     * @param systemMessage 시스템 메세지
     * @return chat ai 응답 객체
     */
    public static ChatResponse call(ChatModel chatModel, String userMessage, String systemMessage) {
    	//TODO(코드블록과 관련된 프롬포트를 주지 않더라도 원하는 클래스 타입으로 응답받을 수 있도록 수정할 것)
        List<Message> messages = List.of(new SystemMessage(systemMessage), new UserMessage(userMessage));
        Prompt chatPrompt = new Prompt(messages);
        return chatModel.call(chatPrompt);
    }

    /**
     * 응답 메세지만 추출
     * @param response chat ai 응답 객체
     * @return 응답 메세지
     */
    public static String getResponseText(ChatResponse response) {
        return Optional.ofNullable(response)
            .map(ChatResponse::getResult)
            .map(Generation::getOutput)
            .map(AssistantMessage::getText)
            .orElseThrow(() -> new IllegalStateException("응답이 없습니다."));
    }   

}