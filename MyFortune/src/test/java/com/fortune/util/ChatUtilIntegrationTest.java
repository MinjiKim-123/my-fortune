package com.fortune.util;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.Map;

import org.junit.jupiter.api.Test;
import org.springframework.ai.chat.client.ChatClient;
import org.springframework.ai.chat.model.ChatResponse;
import org.springframework.ai.vertexai.gemini.VertexAiGeminiChatModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

@SpringBootTest
class ChatUtilIntegrationTest {

	@Autowired
	private VertexAiGeminiChatModel geminiChatModel;

	@Autowired
	private ObjectMapper objectMapper;
	
	@Autowired
	private ChatClient geminiChatClient;

	@Test
	void callGeminiSuccess() throws JsonProcessingException {
		String userMsg = "오늘의 날짜를 알려주세요";
		String sysMsg = """
				아래 json 형식으로 알려줘. 날짜 형식은 yyyyMMdd 패턴에 맞춰서 알려줘
				코드 블록(```json``` 등)은 절대 사용하지 말고
                순수 JSON만 반환할 것.
				{
				    "date": "<날짜>"
				}
				""";

		ChatResponse chatRes = ChatUtil.call(geminiChatModel, userMsg, sysMsg);
		String resText = ChatUtil.getResponseText(chatRes);
		assertNotNull(resText);

		Map<String, String> map = objectMapper.readValue(resText, new TypeReference<Map<String, String>>() {
		});
		assertEquals(LocalDate.now().format(DateTimeFormatter.BASIC_ISO_DATE), map.get("date"));
	}
	
	@Test
	void callGeminiSuccessByChatClient() {
		String userMsg = "오늘의 날짜를 알려주세요";
		String sysMsg = """
				아래 json 형식으로 알려줘. 날짜 형식은 yyyyMMdd 패턴에 맞춰서 알려줘
				{
				    "date": "<날짜>"
				}
				""";
		
		HashMap<String, Object> map = ChatUtil.call(geminiChatClient, userMsg, sysMsg, HashMap.class);
		assertNotNull(map.get("date"));
	}

}
