package com.hulzzuk.voc.model.vo;

import java.sql.Date;

import com.hulzzuk.voc.model.enumeration.VocEnum;

public class VocVO {
	private long vocId;
	private String title;
	private String content;
	private VocEnum category;
	private Date createdAt;
	private Date updatedAt;
	private String userId;
	
	// constructor
	public VocVO() {
		super();
	}

	public VocVO(long vocId, String title, String content, VocEnum category, Date createdAt, Date updatedAt,
			String userId) {
		super();
		this.vocId = vocId;
		this.title = title;
		this.content = content;
		this.category = category;
		this.createdAt = createdAt;
		this.updatedAt = updatedAt;
		this.userId = userId;
	}

	public long getVocId() {
		return vocId;
	}

	public void setVocId(long vocId) {
		this.vocId = vocId;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public VocEnum getCategory() {
		return category;
	}

	public void setCategory(VocEnum category) {
		this.category = category;
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
	
}
