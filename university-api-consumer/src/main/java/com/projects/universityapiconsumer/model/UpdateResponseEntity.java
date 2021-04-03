package com.projects.universityapiconsumer.model;

import com.fasterxml.jackson.annotation.JsonPropertyOrder;

@JsonPropertyOrder({ "status", "message"})
public class UpdateResponseEntity {

    private String status;
    private String message;

    public String getStatus() {
        return status;
    }
    public void setStatus(String status) {
        this.status = status;
    }
    public String getMessage() {
        return message;
    }
    public void setMessage(String message) {
        this.message = message;
    }
    @Override
    public String toString() {
        return "UpdateResponseEntity [status=" + status + ", message=" + message + "]";
    }

}
