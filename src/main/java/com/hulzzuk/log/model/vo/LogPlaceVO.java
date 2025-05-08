package com.hulzzuk.log.model.vo;

public class LogPlaceVO {
	 	private Integer accoId;
	    private Integer restId;
	    private Integer attrId;
	    private String placeName;     // 장소 이름
	    private String category;      // 장소 카테고리 (ex. 숙소, 관광지, 음식점)
	    private int seq;              // 순서
	    private int planDay;          // Day1 / Day2
	    private Double latitude;      // 거리 계산용 위도
	    private Double longitude;     // 거리 계산용 경도

	    public LogPlaceVO() {}

	    public LogPlaceVO(Integer accoId, Integer restId, Integer attrId, String placeName, String category, int seq, int planDay, Double latitude, Double longitude) {
	        this.accoId = accoId;
	        this.restId = restId;
	        this.attrId = attrId;
	        this.placeName = placeName;
	        this.category = category;
	        this.seq = seq;
	        this.planDay = planDay;
	        this.latitude = latitude;
	        this.longitude = longitude;
	    }

	    public Integer getAccoId() {
	        return accoId;
	    }

	    public void setAccoId(Integer accoId) {
	        this.accoId = accoId;
	    }

	    public Integer getRestId() {
	        return restId;
	    }

	    public void setRestId(Integer restId) {
	        this.restId = restId;
	    }

	    public Integer getAttrId() {
	        return attrId;
	    }

	    public void setAttrId(Integer attrId) {
	        this.attrId = attrId;
	    }

	    public String getPlaceName() {
	        return placeName;
	    }

	    public void setPlaceName(String placeName) {
	        this.placeName = placeName;
	    }

	    public String getCategory() {
	        return category;
	    }

	    public void setCategory(String category) {
	        this.category = category;
	    }

	    public int getSeq() {
	        return seq;
	    }

	    public void setSeq(int seq) {
	        this.seq = seq;
	    }

	    public int getPlanDay() {
	        return planDay;
	    }

	    public void setPlanDay(int planDay) {
	        this.planDay = planDay;
	    }

	    public Double getLatitude() {
	        return latitude;
	    }

	    public void setLatitude(Double latitude) {
	        this.latitude = latitude;
	    }

	    public Double getLongitude() {
	        return longitude;
	    }

	    public void setLongitude(Double longitude) {
	        this.longitude = longitude;
	    }

	    @Override
	    public String toString() {
	        return "LogPlaceVO{" +
	                "accoId=" + accoId +
	                ", restId=" + restId +
	                ", attrId=" + attrId +
	                ", placeName='" + placeName + '\'' +
	                ", category='" + category + '\'' +
	                ", seq=" + seq +
	                ", planDay=" + planDay +
	                ", latitude=" + latitude +
	                ", longitude=" + longitude +
	                '}';
	    }
	}
