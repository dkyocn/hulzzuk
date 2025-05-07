package com.hulzzuk.common.enumeration;

public enum ErrorCode {

    // 418 ~ 421, 452 ~ 499 errorCode 사용 가능 ( Client Error )
    // 506, 512 ~ 599 errorCode 사용 가능 ( Server Error )
    NOTICE_NOT_FOUND(418,"해당 ID의 공지사항이 없습니다."),
    USER_NOT_FOUND(419,"해당 ID의 회원 정보가 존재하지 않습니다."),
    PLAN_OUT_OF_BOUNDS(452,"일정의 날짜가 잘못설정되었습니다."),
    PLAN_INSERT_ERROR(506,"일정을 등록하는데 실패하였습니다."),
    PLAN_DELETE_ERROR(512,"일정을 삭제하는데 실패하였습니다."),
    PL_USER_INSERT_ERROR(513,"일정-사용자 중계를 등록하는데 실패하였습니다."),
    PL_TRIP_INSERT_ERROR(514,"일정-장소 중계를 등록하는데 실패하였습니다."),
    REVIEW_NOT_FOUND(420, "해당 ID의 리뷰가 존재하지 않습니다."),
	MAIL_SEND_FAIL(421, "메일 발송이 실패했습니다.");

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
