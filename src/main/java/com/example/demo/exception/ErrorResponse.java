package com.example.demo.exception;

import java.time.LocalDateTime;

public class ErrorResponse {
	
    private String message;
    private LocalDateTime timestamp;
    private int status;

    public ErrorResponse(String message, LocalDateTime timestamp, int status) {
        this.message = message;
        this.timestamp = timestamp;
        this.status = status;
    }
    
    public void setMessage(String message) {
		this.message = message;
	}

	public void setTimestamp(LocalDateTime timestamp) {
		this.timestamp = timestamp;
	}

	public void setStatus(int status) {
		this.status = status;
	}

	public String getMessage() {
        return message;
    }

    public LocalDateTime getTimestamp() {
        return timestamp;
    }

    public int getStatus() {
        return status;
    }
}
