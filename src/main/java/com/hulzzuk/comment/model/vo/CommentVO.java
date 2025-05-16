package com.hulzzuk.comment.model.vo;

import java.sql.Date;

public class CommentVO {
	private long commentId;
	private String content;
	private Date createdAt;
	private Date updateedAt;
	private Long logId;
	private Long vocId;
	private String userId;
	
	public CommentVO() {
		super();
	}

	public CommentVO(long commentId, String content, Date createdAt, Date updateedAt, Long logId, Long vocId,
			String userId) {
		super();
		this.commentId = commentId;
		this.content = content;
		this.createdAt = createdAt;
		this.updateedAt = updateedAt;
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
		return updateedAt;
	}

	public void setUpdateedAt(Date updateedAt) {
		this.updateedAt = updateedAt;
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
