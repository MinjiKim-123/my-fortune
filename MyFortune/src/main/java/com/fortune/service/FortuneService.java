package com.fortune.service;

import java.time.Duration;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;

import org.springframework.ai.chat.model.ChatResponse;
import org.springframework.ai.vertexai.gemini.VertexAiGeminiChatModel;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fortune.dto.FortuneDto;
import com.fortune.dto.users.UserDto;
import com.fortune.exception.FortuneException;
import com.fortune.util.ChatUtil;
import com.fortune.util.StringRedisUtil;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;

/**
 * 운세용 service
 */
@Service
@RequiredArgsConstructor
@Validated
@Log4j2
public class FortuneService {

	private final UserService userService;

	private final VertexAiGeminiChatModel geminiChatModel;
	
	private final StringRedisUtil stringRedisUtil;

	private final ObjectMapper objectMapper;

	/**
	 * 오늘의 운세 조회
	 * @param userIdx
	 * @return
	 */
	public FortuneDto getTodayFortune(@NotNull @Min(1) Integer userIdx) {
		try {
			LocalDateTime now = LocalDateTime.now();
			String today =  DateTimeFormatter.BASIC_ISO_DATE.format(now);
			String redisKey = "todayFortune_" + today + "::" + userIdx;
			String data = stringRedisUtil.getData(redisKey);
			if (data != null) {
				return objectMapper.readValue(data, FortuneDto.class);
			}
	
			UserDto user = userService.getUser(userIdx);
	
			String systemMessage = """
					아래 json 형식으로 운세를 알려줘.
					{
					    "name": "<이름>",
					    "fortune": "<운세>"
					}""";
	
			String birthDate = user.getBirthDt().format(DateTimeFormatter.ISO_DATE);
			String birthTime = user.getBirthDt().format(DateTimeFormatter.ISO_TIME);
			String userMessage = String.format("안녕하세요. 저의 이름은 %s입니다. 생년월일은 %s, 태어난 시간은 %s이고, 성별은 %s입니다. 오늘의 운세를 봐주세요.",
					user.getName(), birthDate, birthTime, user.getGender().getDesc());
	
			ChatResponse response = ChatUtil.call(geminiChatModel, userMessage, systemMessage);
			if (response == null)
				throw new IllegalStateException(
						"Chat response is null. User idx :" + userIdx);
	
			String responseText = ChatUtil.getResponseText(response);
			
			now = LocalDateTime.now();
			Duration duration = Duration.between(now, LocalDateTime.of(now.toLocalDate(), LocalTime.MAX));
			stringRedisUtil.setData(redisKey, responseText, duration);
			
			return objectMapper.readValue(responseText, FortuneDto.class);
		} catch (Exception e) {
			throw new FortuneException("Failed to get today's fortune.", e);
		}
	}
}
