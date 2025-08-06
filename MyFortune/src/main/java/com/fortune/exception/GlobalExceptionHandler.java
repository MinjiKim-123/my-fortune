package com.fortune.exception;

import java.util.HashMap;

import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import jakarta.validation.ConstraintViolationException;
import jakarta.servlet.http.HttpServletRequest;
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
    	//TODO 처리 로직 추가 (ajax or page 요청 구분 및 request 객체에서 요청 값 get 후 로그에 추가)
    	log.error(ex.getMessage(), ex);
    	HashMap<String, Object> model = new HashMap<String, Object>();
        return null;
    }
}
