package com.hulzzuk.log.model.vo;
public class LogReviewVO {

// 중계 테이블용 필드들 (후기 작성 대상 장소들)
private Long logId;
private String accoId;
private String restId;
private String attrId;
private String logContent;
private int planDay;

    
    
	public LogReviewVO() {
		super();
		// TODO Auto-generated constructor stub
	}
	public LogReviewVO(Long logId, String accoId, String restId, String attrId, String logContent, int planDay) {
		super();
		this.logId = logId;
		this.accoId = accoId;
		this.restId = restId;
		this.attrId = attrId;
		this.logContent = logContent;
		this.planDay = planDay;
	}
	public Long getLogId() {
		return logId;
	}
	public void setLogId(Long logId) {
		this.logId = logId;
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
	public String getLogContent() {
		return logContent;
	}
	public void setLogContent(String logContent) {
		this.logContent = logContent;
	}
	public int getPlanDay() {
		return planDay;
	}
	public void setPlanDay(int planDay) {
		this.planDay = planDay;
	}
	
	@Override
	public String toString() {
		return "LogReviewVO [logId=" + logId + ", accoId=" + accoId + ", restId=" + restId + ", attrId=" + attrId
				+ ", logContent=" + logContent + ", planDay=" + planDay + "]";
	}
	
    
    
    
	
}
