package com.example.sendflow.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

//@ResponseStatus(HttpStatus.NOT_FOUND) giúp Spring tự set mã lỗi 404.
//Dùng RuntimeException vì Spring sẽ rollback transaction nếu có lỗi.

//Khi exception được throw, nó sẽ "bay" lên stack trace (từ service/controller lên framework).
//        Spring Boot's DispatcherServlet (xử lý request HTTP) sẽ bắt exception này ở mức global (vì là unhandled exception).
//        Spring sẽ tìm kiếm các ExceptionHandler phù hợp:
//
//        Ưu tiên: Các handler trong @RestControllerAdvice (global handler cho toàn app).
//        Nếu không có, fallback vào @ResponseStatus để set HTTP status.

@ResponseStatus(HttpStatus.NOT_FOUND)
public class ResourceNotFoundException extends RuntimeException {
    public ResourceNotFoundException(String message) {
        super(message);
    }
}
