package com.hulzzuk.common.enumeration;

public enum ErrorCode {

    // 418 ~ 421, 452 ~ 499 errorCode 사용 가능 ( Client Error )
    // 506, 512 ~ 599 errorCode 사용 가능 ( Server Error )
    NOTICE_NOT_FOUND(418,"해당 ID의 공지사항이 없습니다.");

    private int code;
    private String message;

    ErrorCode(int code, String message) {
        this.code = code;
        this.message = message;
    }

    public int getCode() {
        return code;
    }
    public String getMessage() {
        return message;
    }
}
