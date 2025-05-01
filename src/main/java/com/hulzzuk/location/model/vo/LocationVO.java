package com.hulzzuk.location.model.vo;

public class LocationVO {

	private String locId;
	private String placeName;
	private String phone;
	private String addressName;
	private String roadAddressName;
	private Double x;
	private Double y;
	private String placeUrl;
	private String restMenu;
	private String imgPath;
	private String mapPath;

	// constructor
	public LocationVO() {}
	
	public LocationVO(String locId, String placeName, String phone, String addressName, String roadAddressName,
			Double x, Double y, String placeUrl, String restMenu, String imgPath, String mapPath) {
		super();
		this.locId = locId;
		this.placeName = placeName;
		this.phone = phone;
		this.addressName = addressName;
		this.roadAddressName = roadAddressName;
		this.x = x;
		this.y = y;
		this.placeUrl = placeUrl;
		this.restMenu = restMenu;
		this.imgPath = imgPath;
		this.mapPath = mapPath;
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

	public String getRoadAddressName() {
		return roadAddressName;
	}

	public void setRoadAddressName(String roadAddressName) {
		this.roadAddressName = roadAddressName;
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

	public String getMapPath() {
		return mapPath;
	}

	public void setMapPath(String mapPath) {
		this.mapPath = mapPath;
	}
	
	
}
