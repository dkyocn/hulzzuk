package com.hulzzuk.log.model.vo;

public class LogPlaceVO {
    private String accoId;
    private String restId;
    private String attrId;

    private String placeName;     // 장소 이름 (NVL)
    private String category;      // 장소 카테고리 (ex. 숙소, 관광지, 음식점)
    private int seq;              // 순서
    private int planDay;          // Day1 / Day2
    private Double latitude;      // 거리 계산용 위도
    private Double longitude;     // 거리 계산용 경도
    private Double distanceToNext; // 거리계산용

    // JSP에서 ${place.locEnum} 써야하므로 필드추가
    // private Double distanceToNext;  // 추후 거리 계산용, Haversine 결과 값 저장
    private String locEnum; // ACCO, REST, ATTR
    private String id;      // 각 장소별 ID (accoId, restId, attrId 중 하나)

    public LogPlaceVO() {}

    public LogPlaceVO(String accoId, String restId, String attrId, String placeName, String category, int seq, int planDay, Double latitude, Double longitude) {
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

    public String getPlaceName() {
        return placeName;
    }

    public void setPlaceName(String place_Name) {
        this.placeName = place_Name;
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

    // JSP에서 써야하므로 필드 추가하면서 추가함
    public String getLocEnum() {
        if (accoId != null && !accoId.isEmpty()) {
            return "ACCO";
        } else if (restId != null && !restId.isEmpty()) {
            return "REST";
        } else if (attrId != null && !attrId.isEmpty()) {
            return "ATTR";
        } else {
            return "UNKNOWN";
        }
    }

    public String getId() {
        if (accoId != null && !accoId.isEmpty()) {
            return accoId;
        } else if (restId != null && !restId.isEmpty()) {
            return restId;
        } else if (attrId != null && !attrId.isEmpty()) {
            return attrId;
        } else {
            return "";
        }
    }

    public void setLocEnum(String locEnum) {
        this.locEnum = locEnum;
    }

    public void setId(String id) {
        this.id = id;
    }

    // private Double distanceToNext;  // 추후 거리 계산용, Haversine 결과 값 저장
    public Double getDistanceToNext() {
        return distanceToNext;
    }

    public void setDistanceToNext(Double distanceToNext) {
        this.distanceToNext = distanceToNext;
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
