package com.fortune.exception;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.ModelAndView;

import com.fortune.dto.common.AjaxResultDto;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.ConstraintViolationException;
import lombok.extern.slf4j.Slf4j;

/**
 * 오류 처리용 exception handler
 */
@RestControllerAdvice
@Slf4j
public class GlobalExceptionHandler {

    /**
     * 입력 값 오류
     * @param ex 예외 객체
     * @return api 응답 vo
     */
    @ExceptionHandler(exception = { MethodArgumentNotValidException.class, ConstraintViolationException.class, IllegalArgumentException.class})
    public Object handleValidationException(HttpServletRequest request, Exception e) {
        return handle(request, e);
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
    	
    	//TODO 요청 값 꺼내서 로그에 남기기
        log.error(ex.getMessage(), ex);
        
        String accept = request.getHeader("Accept");
        boolean isJsonAccept = accept != null && accept.contains("application/json");
        
        if (isJsonAccept) { 
        	AjaxResultDto<Void> result = AjaxResultDto.failure();
        	return ResponseEntity.ok(result);
        } else {
        	ModelAndView mv = new ModelAndView();
        	mv.setViewName(""); //TODO 오류화면 추가
        	return mv;
        }
    }
}
