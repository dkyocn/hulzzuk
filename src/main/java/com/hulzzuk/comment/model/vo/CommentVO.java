package com.hulzzuk.comment.model.vo;

import java.sql.Date;

public class CommentVO {
	private long commentId;
	private String content;
	private Date createdAt;
	private Date updatedAt;
	private Long logId;
	private Long vocId;
	private String userId;
	
	public CommentVO() {
		super();
	}

	public CommentVO(String content, Long logId, Long vocId, String userId) {
		this.content = content;
		this.logId = logId;
		this.vocId = vocId;
		this.userId = userId;
	}

	public CommentVO(long commentId, String content, Date createdAt, Date updatedAt, Long logId, Long vocId,
					 String userId) {
		super();
		this.commentId = commentId;
		this.content = content;
		this.createdAt = createdAt;
		this.updatedAt = updatedAt;
		this.logId = logId;
		this.vocId = vocId;
		this.userId = userId;
	}

	public long getCommentId() {
		return commentId;
	}

	public void setCommentId(long commentId) {
		this.commentId = commentId;
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

	public Date getUpdateedAt() {
		return updatedAt;
	}

	public void setUpdatedAt(Date updatedAt) {
		this.updatedAt = updatedAt;
	}

	public Long getLogId() {
		return logId;
	}

	public void setLogId(Long logId) {
		this.logId = logId;
	}

	public Long getVocId() {
		return vocId;
	}

	public void setVocId(Long vocId) {
		this.vocId = vocId;
	}

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}
	
	
}
