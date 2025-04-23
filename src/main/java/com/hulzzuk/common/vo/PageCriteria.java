package com.hulzzuk.common.vo;

import java.io.Serializable;

public class PageCriteria implements Serializable {
	
	private int pageNum;
	private int numPerPage;
	
	public PageCriteria() {
		this(1, 10);
	}
	
	public PageCriteria(int pageNum, int numPerPage) {
		this.pageNum = pageNum;
		this.numPerPage = numPerPage;
	}
	
	public int getPageNum() {
		return pageNum;
	}
	
	public void setpageNum(int pageNum) {
		if (pageNum <= 0) {
			this.pageNum = 1;
		} else {
			this.pageNum = pageNum;
		}
		
		this.pageNum = pageNum;
	}
	
	public int getnumPerPage() {
		return numPerPage;
	}
	
	public void setnumPerPage(int numPerPage) {
		if (numPerPage <= 0 || numPerPage > 30) {
			this.numPerPage = 30;
		} else {
			this.numPerPage = numPerPage;
		}
		
		this.numPerPage = numPerPage;
	}
	
	public int getStartPage() {
		return (this.pageNum - 1) * this.numPerPage + 1;
	}

}
