package com.hulzzuk.log.model.vo;

public class PageCriteria {
	
	private int start;
	private int amount;
	public PageCriteria() {
		
		
		super();
	}
	
	public PageCriteria(int start, int amount) {
		super();
		this.start = start;
		this.amount = amount;
	}

	public int getStart() {
		return start;
	}

	public void setStart(int start) {
		this.start = start;
	}

	public int getAmount() {
		return amount;
	}

	public void setAmount(int amount) {
		this.amount = amount;
	}
	
	
	

}
