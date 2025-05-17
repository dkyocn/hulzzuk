package com.hulzzuk.recomment.model.vo;

import java.sql.Date;

public class RecommentVO {
	private Long recoId;
	private String content;
	private Date createdAt;
	private Date updatedAt;
	private String userId;
	private Long commentId;
	
	
	public RecommentVO() {
		super();
	}

	public RecommentVO(Long recoId, String content, Date createdAt, Date updatedAt, String userId, Long commentId) {
		super();
		this.recoId = recoId;
		this.content = content;
		this.createdAt = createdAt;
		this.updatedAt = updatedAt;
		this.userId = userId;
		this.commentId = commentId;
	}

	public Long getRecoId() {
		return recoId;
	}

	public void setRecoId(Long recoId) {
		this.recoId = recoId;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public Date getCreatedAt() {
		return createdAt;
	}

	public void setCreatedAt(Date createdAt) {
		this.createdAt = createdAt;
	}

	public Date getUpdatedAt() {
		return updatedAt;
	}

	public void setUpdatedAt(Date updatedAt) {
		this.updatedAt = updatedAt;
	}

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public Long getCommentId() {
		return commentId;
	}

	public void setCommentId(Long commentId) {
		this.commentId = commentId;
	}
	
	
}
