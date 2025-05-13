package com.hulzzuk.user.model.vo;

import java.sql.Date;
import java.util.Map;

public class SocialUserVO implements java.io.Serializable {
	private static final long serialVersionUID = -1962768785117036477L;
	
	private Long id;
	private Boolean hasSignedUp;
	private Date connectedAt;
	private Date synchedAt;
	private Map<String, String> properties;
	private KakaoAccountVO kakaoAccount;
	private Map<String, String> forPartner;
	
	public SocialUserVO() {
		super();
	}
	
	public SocialUserVO(Long id, Boolean hasSignedUp, Date connectedAt, Date synchedAt, Map<String, String> properties,
			KakaoAccountVO kakaoAccount, Map<String, String> forPartner) {
		super();
		this.id = id;
		this.hasSignedUp = hasSignedUp;
		this.connectedAt = connectedAt;
		this.synchedAt = synchedAt;
		this.properties = properties;
		this.kakaoAccount = kakaoAccount;
		this.forPartner = forPartner;
	}

	public Long getId() {
		return id;
	}


	public void setId(Long id) {
		this.id = id;
	}


	public Boolean getHasSignedUp() {
		return hasSignedUp;
	}


	public void setHasSignedUp(Boolean hasSignedUp) {
		this.hasSignedUp = hasSignedUp;
	}


	public Date getConnectedAt() {
		return connectedAt;
	}


	public void setConnectedAt(Date connectedAt) {
		this.connectedAt = connectedAt;
	}


	public Date getSynchedAt() {
		return synchedAt;
	}


	public void setSynchedAt(Date synchedAt) {
		this.synchedAt = synchedAt;
	}


	public Map<String, String> getProperties() {
		return properties;
	}


	public void setProperties(Map<String, String> properties) {
		this.properties = properties;
	}


	public KakaoAccountVO getKakaoAccount() {
		return kakaoAccount;
	}


	public void setKakaoAccount(KakaoAccountVO kakaoAccount) {
		this.kakaoAccount = kakaoAccount;
	}


	public Map<String, String> getForPartner() {
		return forPartner;
	}


	public void setForPartner(Map<String, String> forPartner) {
		this.forPartner = forPartner;
	}


	

	public class KakaoAccountVO {
	    private Boolean profileNeedsAgreement;
	    private ProfileVO profile;
	    private Boolean hasEmail;
	    private Boolean emailNeedsAgreement;
	    private String email;
	    private String ageRange;
	    private String gender;
	    private String birthyear;
	    private String birthday;
	    private String birthdayType;
	
	   
		public KakaoAccountVO() {
			super();
		}

		public KakaoAccountVO(Boolean profileNeedsAgreement, ProfileVO profile, Boolean hasEmail,
				Boolean emailNeedsAgreement, String email, String ageRange, String gender, String birthyear,
				String birthday, String birthdayType) {
			super();
			this.profileNeedsAgreement = profileNeedsAgreement;
			this.profile = profile;
			this.hasEmail = hasEmail;
			this.emailNeedsAgreement = emailNeedsAgreement;
			this.email = email;
			this.ageRange = ageRange;
			this.gender = gender;
			this.birthyear = birthyear;
			this.birthday = birthday;
			this.birthdayType = birthdayType;
		}
		
		 
		public Boolean getProfileNeedsAgreement() {
			return profileNeedsAgreement;
		}


		public void setProfileNeedsAgreement(Boolean profileNeedsAgreement) {
			this.profileNeedsAgreement = profileNeedsAgreement;
		}


		public ProfileVO getProfile() {
			return profile;
		}


		public void setProfile(ProfileVO profile) {
			this.profile = profile;
		}


		public Boolean getHasEmail() {
			return hasEmail;
		}


		public void setHasEmail(Boolean hasEmail) {
			this.hasEmail = hasEmail;
		}


		public Boolean getEmailNeedsAgreement() {
			return emailNeedsAgreement;
		}


		public void setEmailNeedsAgreement(Boolean emailNeedsAgreement) {
			this.emailNeedsAgreement = emailNeedsAgreement;
		}


		public String getEmail() {
			return email;
		}


		public void setEmail(String email) {
			this.email = email;
		}


		public String getAgeRange() {
			return ageRange;
		}


		public void setAgeRange(String ageRange) {
			this.ageRange = ageRange;
		}


		public String getGender() {
			return gender;
		}


		public void setGender(String gender) {
			this.gender = gender;
		}


		public String getBirthyear() {
			return birthyear;
		}


		public void setBirthyear(String birthyear) {
			this.birthyear = birthyear;
		}


		public String getBirthday() {
			return birthday;
		}


		public void setBirthday(String birthday) {
			this.birthday = birthday;
		}


		public String getBirthdayType() {
			return birthdayType;
		}


		public void setBirthdayType(String birthdayType) {
			this.birthdayType = birthdayType;
		}

		public class ProfileVO {
	        private String nickname;
	        private String thumbnailImageUrl;
	        private String profileImageUrl;
	        private Boolean isDefaultImage;
	        
	        
			public ProfileVO() {
				super();
			}

			public ProfileVO(String nickname, String thumbnailImageUrl, String profileImageUrl,
					Boolean isDefaultImage) {
				super();
				this.nickname = nickname;
				this.thumbnailImageUrl = thumbnailImageUrl;
				this.profileImageUrl = profileImageUrl;
				this.isDefaultImage = isDefaultImage;
			}

			public String getNickname() {
				return nickname;
			}

			public void setNickname(String nickname) {
				this.nickname = nickname;
			}

			public String getThumbnailImageUrl() {
				return thumbnailImageUrl;
			}

			public void setThumbnailImageUrl(String thumbnailImageUrl) {
				this.thumbnailImageUrl = thumbnailImageUrl;
			}

			public String getProfileImageUrl() {
				return profileImageUrl;
			}

			public void setProfileImageUrl(String profileImageUrl) {
				this.profileImageUrl = profileImageUrl;
			}

			public Boolean getIsDefaultImage() {
				return isDefaultImage;
			}

			public void setIsDefaultImage(Boolean isDefaultImage) {
				this.isDefaultImage = isDefaultImage;
			}
	        
	    }
	}
}
