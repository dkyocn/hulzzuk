package com.hulzzuk.common.vo;

public class PopUpVO {
	private String message;
	private String actionUrl;
	private int width;
	private int height;
	
	public PopUpVO(int width, int height) {
		super();
		this.width = width;
		this.height = height;
	}

	public PopUpVO(String message, String actionUrl, int width, int height) {
		super();
		this.message = message;
		this.actionUrl = actionUrl;
		this.width = width;
		this.height = height;
	}
	
	public String getMessage() {
		return message;
	}
	public void setMessage(String message) {
		this.message = message;
	}
	public String getactionUrl() {
		return actionUrl;
	}
	public void setactionUrl(String actionUrl) {
		this.actionUrl = actionUrl;
	}
	public int getWidth() {
		return width;
	}
	public void setWidth(int width) {
		this.width = width;
	}
	public int getHeight() {
		return height;
	}
	public void setHeight(int height) {
		this.height = height;
	}

}
