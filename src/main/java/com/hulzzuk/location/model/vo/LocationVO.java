package com.hulzzuk.location.model.vo;

import com.hulzzuk.location.model.enumeration.LocationEnum;

public class LocationVO {

	private String locId;
	private String placeName;
	private String phone;
	private String addressName;
	private Double x;
	private Double y;
	private String category;
	private String placeUrl;
	private String restMenu;
	private String imgPath;
	private LocationEnum locationEnum;
	private int loveCount;

	// constructor
	public LocationVO() {}

	public LocationVO(String locId, String placeName, String phone, String addressName, Double x, Double y,
			String category, String placeUrl, String restMenu, String imgPath, LocationEnum locationEnum,
			int loveCount) {
		super();
		this.locId = locId;
		this.placeName = placeName;
		this.phone = phone;
		this.addressName = addressName;
		this.x = x;
		this.y = y;
		this.category = category;
		this.placeUrl = placeUrl;
		this.restMenu = restMenu;
		this.imgPath = imgPath;
		this.locationEnum = locationEnum;
		this.loveCount = loveCount;
	}

	public int getLoveCount() {
		return loveCount;
	}

	public void setLoveCount(int loveCount) {
		this.loveCount = loveCount;
	}

	public String getCategory() {
		return category;
	}

	public void setCategory(String category) {
		this.category = category;
	}

	// getters and setters
	public String getLocId() {
		return locId;
	}

	public void setLocId(String locId) {
		this.locId = locId;
	}

	public String getPlaceName() {
		return placeName;
	}

	public void setPlaceName(String placeName) {
		this.placeName = placeName;
	}

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public String getAddressName() {
		return addressName;
	}

	public void setAddressName(String addressName) {
		this.addressName = addressName;
	}
	
	public Double getX() {
		return x;
	}

	public void setX(Double x) {
		this.x = x;
	}

	public Double getY() {
		return y;
	}

	public void setY(Double y) {
		this.y = y;
	}

	public String getPlaceUrl() {
		return placeUrl;
	}

	public void setPlaceUrl(String placeUrl) {
		this.placeUrl = placeUrl;
	}

	public String getRestMenu() {
		return restMenu;
	}

	public void setRestMenu(String restMenu) {
		this.restMenu = restMenu;
	}
	
	public String getImgPath() {
		return imgPath;
	}

	public void setImgPath(String imgPath) {
		this.imgPath = imgPath;
	}

	public LocationEnum getLocationEnum() {
		return locationEnum;
	}

	public void setLocationEnum(LocationEnum locationEnum) {
		this.locationEnum = locationEnum;
	}
}
