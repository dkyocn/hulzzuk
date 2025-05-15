package com.hulzzuk.user.model.vo;

public class NaverUserVO implements java.io.Serializable{
	private static final long serialVersionUID = 3669058161907292464L;
	
	private String resultcode;
	private String message;
	private ResponseVO response;
	
	public NaverUserVO() {
		super();
	}

	public NaverUserVO(String resultcode, String message, ResponseVO response) {
		super();
		this.resultcode = resultcode;
		this.message = message;
		this.response = response;
	}

	public String getResultcode() {
		return resultcode;
	}

	public void setResultcode(String resultcode) {
		this.resultcode = resultcode;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public ResponseVO getResponse() {
		return response;
	}

	public void setResponse(ResponseVO response) {
		this.response = response;
	}
	
	public class ResponseVO {
		private String id;
		private String nickname;
		private String email;
		private String gender;
		private String age;
		private String birthday;
		private String profile_image;
		private String birthyear;
		private String mobile;
		
		public ResponseVO() {
			super();
		}

		public ResponseVO(String id, String nickname, String email, String gender, String age, String birthday,
				String profile_image, String birthyear, String mobile) {
			super();
			this.id = id;
			this.nickname = nickname;
			this.email = email;
			this.gender = gender;
			this.age = age;
			this.birthday = birthday;
			this.profile_image = profile_image;
			this.birthyear = birthyear;
			this.mobile = mobile;
		}

		public String getId() {
			return id;
		}

		public void setId(String id) {
			this.id = id;
		}

		public String getNickname() {
			return nickname;
		}

		public void setNickname(String nickname) {
			this.nickname = nickname;
		}

		public String getEmail() {
			return email;
		}

		public void setEmail(String email) {
			this.email = email;
		}

		public String getGender() {
			return gender;
		}

		public void setGender(String gender) {
			this.gender = gender;
		}

		public String getAge() {
			return age;
		}

		public void setAge(String age) {
			this.age = age;
		}

		public String getBirthday() {
			return birthday;
		}

		public void setBirthday(String birthday) {
			this.birthday = birthday;
		}

		public String getProfile_image() {
			return profile_image;
		}

		public void setProfile_image(String profile_image) {
			this.profile_image = profile_image;
		}

		public String getBirthyear() {
			return birthyear;
		}

		public void setBirthyear(String birthyear) {
			this.birthyear = birthyear;
		}

		public String getMobile() {
			return mobile;
		}

		public void setMobile(String mobile) {
			this.mobile = mobile;
		}

		
		
	}
}
