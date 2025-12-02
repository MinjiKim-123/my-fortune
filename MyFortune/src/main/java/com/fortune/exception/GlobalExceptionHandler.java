package com.fortune.exception;

import java.util.Map;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.NoHandlerFoundException;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fortune.dto.common.AjaxResultDto;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.ConstraintViolationException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

/**
 * 오류 처리용 exception handler
 */
@RestControllerAdvice
@Slf4j
@RequiredArgsConstructor
public class GlobalExceptionHandler {

	private final ObjectMapper objectMapper; 
	
    /**
     * 입력 값 오류
     * @param ex 예외 객체
     * @return api 응답 vo
     */
    @ExceptionHandler(exception = { MethodArgumentNotValidException.class, ConstraintViolationException.class, IllegalArgumentException.class})
    public Object handleValidationException(HttpServletRequest request, Exception e) {
        return handle(request, e);
    }

    @ExceptionHandler(NoHandlerFoundException.class)
    public String notFoundHandler() {
        return "/error/404";
    }
    
    /**
     * 그 외 모든 오류
     * @param request
     * @param e
     * @return
     */
    @ExceptionHandler(exception = Exception.class)
    public Object handleException(HttpServletRequest request, Exception e) {
        return handle(request, e);
    }

    private Object handle(HttpServletRequest request, Throwable ex) {
    	Map<String, String[]> parameters = request.getParameterMap();
    	String param = null;
    	if (parameters != null) {
    		try {
				param = objectMapper.writeValueAsString(parameters);
			} catch (JsonProcessingException e) {
				log.error("요청 파라미터 map to string 변환 실패.", e);
			}
    	}
    		
    	log.error("Request parameters: {}\n", param);
        log.error("Exception: ", ex);
        
        String accept = request.getHeader("Accept");
        boolean isJsonAccept = accept != null && accept.contains("application/json");
        
        if (isJsonAccept) { 
        	AjaxResultDto<Void> result = AjaxResultDto.failure();
        	return ResponseEntity.ok(result);
        } else {
        	ModelAndView mv = new ModelAndView();
        	mv.setViewName("/error/DefError");
        	return mv;
        }
    }
}
