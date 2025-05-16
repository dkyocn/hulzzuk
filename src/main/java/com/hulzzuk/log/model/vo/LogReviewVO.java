package com.hulzzuk.log.model.vo;
public class LogReviewVO {
	/**
	 * 여행 일자(planDay)별 장소 + 후기 VO
	 * - 기존 LogPlaceVO + 후기 필드 (logContent 등) 통합
	 * - 렌더링에 필요한 정보 포함 (placeName, locEnum, id 등)
	 */
	
//  장소+후기 통합 VO (기존 PlaceVO 역할 포함) 
	private Long logId;
	private long planId;
    private String accoId;
    private String restId;
    private String attrId;
    private int seq;         // 순서
    private int planDay;		// Day1 / Day2
    private String category; // ACCO, REST, ATTR// 장소 카테고리 (ex. 숙소, 관광지, 음식점)

    private String logContent;
    // 아래 필드들은 기존 LogPlaceVO 일부이지만 리뷰 렌더링에 필요 
    

    private String placeName;     // 장소 이름 (NVL)
    private Double latitude;      // 거리 계산용 위도
    private Double longitude;     // 거리 계산용 경도
    private Double distanceToNext; // 거리계산용

    // JSP에서 ${place.locEnum} 써야하므로 필드추가
    // private Double distanceToNext;  // 추후 거리 계산용, Haversine 결과 값 저장
    private String locEnum; // ACCO, REST, ATTR
    private String id;      // 각 장소별 ID (accoId, restId, attrId 중 하나)

	public LogReviewVO() {
		super();
	}

	public LogReviewVO(Long logId, long planId, String accoId, String restId, String attrId, int seq, int planDay,
			String category, String logContent, String placeName, Double latitude, Double longitude,
			Double distanceToNext, String locEnum, String id) {
		super();
		this.logId = logId;
		this.planId = planId;
		this.accoId = accoId;
		this.restId = restId;
		this.attrId = attrId;
		this.seq = seq;
		this.planDay = planDay;
		this.category = category;
		this.logContent = logContent;
		this.placeName = placeName;
		this.latitude = latitude;
		this.longitude = longitude;
		this.distanceToNext = distanceToNext;
		this.locEnum = locEnum;
		this.id = id;
	}

	public Long getLogId() {
		return logId;
	}

	public void setLogId(Long logId) {
		this.logId = logId;
	}

	public long getPlanId() {
		return planId;
	}

	public void setPlanId(long planId) {
		this.planId = planId;
	}

	public String getAccoId() {
		return accoId;
	}

	public void setAccoId(String accoId) {
		this.accoId = accoId;
	}

	public String getRestId() {
		return restId;
	}

	public void setRestId(String restId) {
		this.restId = restId;
	}

	public String getAttrId() {
		return attrId;
	}

	public void setAttrId(String attrId) {
		this.attrId = attrId;
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

	public String getCategory() {
		return category;
	}

	public void setCategory(String category) {
		this.category = category;
	}

	public String getLogContent() {
		return logContent;
	}

	public void setLogContent(String logContent) {
		this.logContent = logContent;
	}

	public String getPlaceName() {
		return placeName;
	}

	public void setPlaceName(String placeName) {
		this.placeName = placeName;
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

	public Double getDistanceToNext() {
		return distanceToNext;
	}

	public void setDistanceToNext(Double distanceToNext) {
		this.distanceToNext = distanceToNext;
	}

	public String getLocEnum() {
		return locEnum;
	}

	public void setLocEnum(String locEnum) {
		this.locEnum = locEnum;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	@Override
	public String toString() {
		return "LogReviewVO [logId=" + logId + ", planId=" + planId + ", accoId=" + accoId + ", restId=" + restId
				+ ", attrId=" + attrId + ", seq=" + seq + ", planDay=" + planDay + ", category=" + category
				+ ", logContent=" + logContent + ", placeName=" + placeName + ", latitude=" + latitude + ", longitude="
				+ longitude + ", distanceToNext=" + distanceToNext + ", locEnum=" + locEnum + ", id=" + id + "]";
	}

	

    
	
}
