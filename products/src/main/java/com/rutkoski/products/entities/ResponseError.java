package com.rutkoski.products.entities;

import com.fasterxml.jackson.annotation.JsonFormat;

import java.time.Instant;
import java.util.List;

public class ResponseError {
    private int status;
    private String error;
    private String path;
    private List<String> messages;
    @JsonFormat(pattern="yyyy-MM-dd HH:mm:ss", timezone = "UTC")
    private Instant instant;

    public ResponseError() {
    }

    public ResponseError(int status, String error, String path, List<String> messages, Instant instant) {
        this.status = status;
        this.error = error;
        this.path = path;
        this.messages = messages;
        this.instant = instant;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public String getError() {
        return error;
    }

    public void setError(String error) {
        this.error = error;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public List<String> getMessages() {
        return messages;
    }

    public void setMessages(List<String> messages) {
        this.messages = messages;
    }

    public Instant getInstant() {
        return instant;
    }

    public void setInstant(Instant instant) {
        this.instant = instant;
    }
}
