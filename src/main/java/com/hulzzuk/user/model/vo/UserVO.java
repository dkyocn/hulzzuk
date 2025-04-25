package com.hulzzuk.user.model.vo;

import java.sql.Date;
import java.time.LocalDate;
import java.time.Period;

public class UserVO implements java.io.Serializable {
	private static final long serialVersionUID = -163877130673884040L;
	
	private String userId;			//	USER_ID	VARCHAR2(30 BYTE)
	private String userPwd; 		//	USER_PWD	VARCHAR2(255 BYTE)
	private String userNick;		//	USER_NICK	VARCHAR2(20 BYTE)
	private String gender;			//	USER_GENDER	CHAR(1 BYTE)
	private Date userAge;			//	USER_AGE	DATE
	private String userProfile;		//  USER_PROFILE VARCHAR2(500 BYTE)
	private String userKey;			//	USER_KEY	VARCHAR2(30 BYTE)
	private String userPath; 		//	USER_PATH	VARCHAR2(30 BYTE)
	private String userRefreshCode;	//	USER_REFRESHCODE	VARCHAR2(255 BYTE)
	private String adminYN;			//	ADMIN_YN	VARCHAR2(4 BYTE)
	
	// 나이 계산 메소드
	public int getAge() {
		if(userAge == null) return 0;
		LocalDate birth = userAge.toLocalDate();
		LocalDate today = LocalDate.now();
		return Period.between(birth, today).getYears();
	}
	
	public UserVO() {
		super();
	}

	public UserVO(String userId, String userPwd, String userNick, String gender, Date userAge, String userProfile,
			String userKey,	String userPath, String userRefreshCode, String adminYN) {
		super();
		this.userId = userId;
		this.userPwd = userPwd;
		this.userNick = userNick;
		this.gender = gender;
		this.userAge = userAge;
		this.userProfile = userProfile;
		this.userKey = userKey;
		this.userPath = userPath;
		this.userRefreshCode = userRefreshCode;
		this.adminYN = adminYN;
	}

	// getters & setters
	
	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public String getUserPwd() {
		return userPwd;
	}

	public void setUserPwd(String userPwd) {
		this.userPwd = userPwd;
	}

	public String getUserNick() {
		return userNick;
	}

	public void setUserNick(String userNick) {
		this.userNick = userNick;
	}

	public String getGender() {
		return gender;
	}

	public String getUserProfile() {
		return userProfile;
	}

	public void setUserProfile(String userProfile) {
		this.userProfile = userProfile;
	}

	public void setGender(String gender) {
		this.gender = gender;
	}

	public java.sql.Date getUserAge() {
		return userAge;
	}

	public void setUserAge(java.sql.Date userAge) {
		this.userAge = userAge;
	}

	public String getUserKey() {
		return userKey;
	}

	public void setUserKey(String userKey) {
		this.userKey = userKey;
	}

	public String getUserPath() {
		return userPath;
	}

	public void setUserPath(String userPath) {
		this.userPath = userPath;
	}

	public String getUserRefreshCode() {
		return userRefreshCode;
	}

	public void setUserRefreshCode(String userRefreshCode) {
		this.userRefreshCode = userRefreshCode;
	}

	public String getAdminYN() {
		return adminYN;
	}

	public void setAdminYN(String adminYN) {
		this.adminYN = adminYN;
	}

	
	
}





