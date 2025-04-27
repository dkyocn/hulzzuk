package com.hulzzuk.review.model.vo;

import java.sql.Date;

public class ReviewVO {
	private long reviewId;
	private double userRev;
	private String accoId;
	private String restId;
	private String attrId;
	private String userReviewText;
	private Date createAt;
	private Date updateAt;
	private String userId;
	
	//Constructor
	public ReviewVO() {}

	public ReviewVO(long reviewId, double userRev, String accoId, String restId, String attrId, String userReviewText,
			Date createAt, Date updateAt, String userId) {
		super();
		this.reviewId = reviewId;
		this.userRev = userRev;
		this.accoId = accoId;
		this.restId = restId;
		this.attrId = attrId;
		this.userReviewText = userReviewText;
		this.createAt = createAt;
		this.updateAt = updateAt;
		this.userId = userId;
	}

	public long getReviewId() {
		return reviewId;
	}

	public void setReviewId(long reviewId) {
		this.reviewId = reviewId;
	}

	public double getUserRev() {
		return userRev;
	}

	public void setUserRev(double userRev) {
		this.userRev = userRev;
	}

	public Date getCreateAt() {
		return createAt;
	}

	public void setCreateAt(Date createAt) {
		this.createAt = createAt;
	}

	public Date getUpdateAt() {
		return updateAt;
	}

	public void setUpdateAt(Date updateAt) {
		this.updateAt = updateAt;
	}

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public String getAccoId() {
		return accoId;
	}

	public void setAccoId(String accoId) {
		this.accoId = accoId;
	}

	public String getRestId() {
		return restId;
	}

	public void setRestId(String restId) {
		this.restId = restId;
	}

	public String getAttrId() {
		return attrId;
	}

	public void setAttrId(String attrId) {
		this.attrId = attrId;
	}

	public String getUserReviewText() {
		return userReviewText;
	}

	public void setUserReviewText(String userReviewText) {
		this.userReviewText = userReviewText;
	}
	
	
	
	
}
