package com.hulzzuk.common.util;

public class ErrorResponse {
    private String errorCode;
    private String type;
    private String message;

    public ErrorResponse(String errorCode, String type, String message) {
        this.errorCode = errorCode;
        this.type = type;
        this.message = message;
    }

    // Getter와 Setter
    public String getErrorCode() {
        return errorCode;
    }

    public void setErrorCode(String errorCode) {
        this.errorCode = errorCode;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
