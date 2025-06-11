package com.fortune.service;

import java.time.format.DateTimeFormatter;

import org.springframework.ai.chat.model.ChatResponse;
import org.springframework.ai.vertexai.gemini.VertexAiGeminiChatModel;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fortune.dto.FortuneDto;
import com.fortune.dto.users.UserDto;
import com.fortune.util.ChatUtil;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;

@Service
@RequiredArgsConstructor
@Validated
@Log4j2
public class FoutuneService {

	private final UserService userService;

	private final VertexAiGeminiChatModel geminiChatModel;

	private final ObjectMapper objectMapper;

    // fortuneService 호출
    public FortuneDto getTodayFortune(@NotNull @Min(1) Integer userIdx) {

    	//TODO REDIS 조회해서 있으면 chat없이 해당 데이터 응답하도록 추가
    	
    	UserDto user = userService.getUser(userIdx);
    	
        String systemMessage =""" 
        아래 json 형식으로 운세를 알려줘.
        {
            "name": "<이름>",
            "fortune": "<운세>"
        }""";
        
        String birthDate = user.getBirthDt().format(DateTimeFormatter.ISO_DATE);
        String birthTime = user.getBirthDt().format(DateTimeFormatter.ISO_TIME);
        String userMessage = String.format(
            "안녕하세요. 저의 이름은 %s입니다. 생년월일은 %s, 태어난 시간은 %s이고, 성별은 %s입니다. 오늘의 운세를 봐주세요.",
            user.getName(), birthDate, birthTime, user.getGender().getDesc()
        );
        
        ChatResponse response = ChatUtil.call(geminiChatModel, userMessage, systemMessage);
        if(response == null) 
        	throw new IllegalStateException("Failed to get today's fortuce. Chat response is null. User idx :"+ userIdx);
        
        String responseText = ChatUtil.getResponseText(response);
        //TODO 응답 데이터 REDIS 저장 및 JSON TO DTO 로직 추가 예정
        return null;
    }
 
}
