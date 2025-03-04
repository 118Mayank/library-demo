package com.demo.library.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.http.HttpStatus;

import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ResponseDTO {
    private HttpStatus status;
    private LocalDateTime timestamp;
    private Object message;
    private Object data;
    private String path;

    private String error;
    public ResponseDTO(LocalDateTime timestamp, HttpStatus status, String error, Object message, String path) {
        this.timestamp = timestamp;
        this.status = status;
        this.error = error;
        this.message = message;
        this.path = path;
    }

    public ResponseDTO(HttpStatus status, LocalDateTime timestamp, Object data, Object message,String path) {
        this.status = status;
        this.timestamp = timestamp;
        this.data = data;
        this.message = message;
        this.path = path;
    }

    public ResponseDTO(HttpStatus status, LocalDateTime timestamp,  Object message,String path) {
        this.status = status;
        this.timestamp = timestamp;
        this.message = message;
        this.path = path;
    }

}


