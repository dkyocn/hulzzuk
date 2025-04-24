package com.hulzzuk.notice.model.vo;

import java.io.Serializable;
import java.sql.Date;

import com.hulzzuk.common.vo.PageMaker;
import com.hulzzuk.notice.enummeration.Category;

public class NoticeVO extends PageMaker implements Serializable {

	private long noticeId; // 공지사항 id
	private String title; // 제목
	private String content; // 내용
	private int isPinned; // 고정 여부
	private Date createdAt; // 최초 생성 일자
	private Date updatedAt; // 최종 생성 일자
	private Category category; // 카테고리 (NOICE, FAQ)



	public NoticeVO() {
		super();
	}

	public NoticeVO(long noticeId, String title, String content, int isPinned, Date createdAt, Date updatedAt,
			Category category) {
		super();
		this.noticeId = noticeId;
		this.title = title;
		this.content = content;
		this.isPinned = isPinned;
		this.createdAt = createdAt;
		this.updatedAt = updatedAt;
		this.category = category;
	}



	// getter & setter
	public long getNoticeId() {
		return noticeId;
	}
	public void setNoticeId(long noticeId) {
		this.noticeId = noticeId;
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
	public int isPinned() {
		return isPinned;
	}
	public void setPinned(int isPinned) {
		this.isPinned = isPinned;
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
	public Category getCategory() {
		return category;
	}
	public void setCategory(Category category) {
		this.category = category;
	}


}
