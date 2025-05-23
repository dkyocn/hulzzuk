package com.hulzzuk.common.enumeration;

public enum ErrorCode {

    // 418 ~ 421, 452 ~ 499 errorCode 사용 가능 ( Client Error )
    // 506, 512 ~ 599 errorCode 사용 가능 ( Server Error )
    NOTICE_NOT_FOUND(418,"해당 ID의 공지사항이 없습니다."),
    NOTICE_INSERT_ERROR(522,"공지사항을 등록하는데 실패하였습니다."),
    NOTICE_UPDATE_ERROR(523,"공지사항을 수정하는데 실패하였습니다."),
    NOTICE_DELETE_ERROR(524,"공지사항을 삭제하는데 실패하였습니다."),
    FAQ_NOT_FOUND(418,"해당 ID의 FAQ가 없습니다."),
    FAQ_INSERT_ERROR(522,"FAQ를 등록하는데 실패하였습니다."),
    FAQ_UPDATE_ERROR(523,"FAQ를 수정하는데 실패하였습니다."),
    FAQ_DELETE_ERROR(524,"FAQ를 삭제하는데 실패하였습니다."),
    USER_NOT_FOUND(419,"해당 ID의 회원 정보가 존재하지 않습니다."),
    USER_UPDATE_ERROR(422,"회원 정보 수정을 실패하였습니다."),
    USER_DELETE_ERROR(515, "회원 탈퇴에 실패하였습니다."),
    PLAN_OUT_OF_BOUNDS(452,"일정의 날짜가 잘못설정되었습니다."),
    PLAN_NOT_FOUND(521,"해당 아이디의 일정을 찾을 수 없습니다."),
    PLAN_INSERT_ERROR(506,"일정을 등록하는데 실패하였습니다."),
    PLAN_UPDATE_ERROR(516,"일정을 수정하는데 실패하였습니다."),
    PLAN_DELETE_ERROR(512,"일정을 삭제하는데 실패하였습니다."),
    PL_USER_INSERT_ERROR(513,"일정-사용자 중계를 등록하는데 실패하였습니다."),
    PL_TRIP_INSERT_ERROR(514,"일정-장소 중계를 등록하는데 실패하였습니다."),
    PL_TRIP_DELETE_ERROR(517,"일정-장소 중계를 삭제하는데 실패하였습니다."),
    CHECKLIST_INSERT_ERROR(518,"체크리스트 항목을 추가하는데 실패하였습니다."),
    CHECKLIST_UPDATE_ERROR(519,"체크리스트 항목을 수정하는데 실패하였습니다."),
    CHECKLIST_DELETE_ERROR(520,"체크리스트 항목을 삭제하는데 실패하였습니다."),
    REVIEW_NOT_FOUND(420, "해당 ID의 리뷰가 존재하지 않습니다."),
    COMMENT_NOT_FOUND(422,"해당 ID의 댓들이 존재하지 않습니다."),
    COMMENT_INSERT_ERROR(598,"댓글을 생성하는데 실패하였습니다."),
    COMMENT_DELETE_ERROR(599,"댓글을 삭제하는데 실패하였습니다."),
    RECOMMENT_INSERT_ERROR(601,"대댓글을 생성하는데 실패하였습니다."),
    RECOMMENT_DELETE_ERROR(602,"대댓글을 삭제하는데 실패하였습니다."),
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
