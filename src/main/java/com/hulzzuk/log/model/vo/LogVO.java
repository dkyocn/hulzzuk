package com.hulzzuk.log.model.vo;

import java.sql.Date;

public class LogVO {

 
	private long logId;
    private String imagePath;
    private String logTitle;
    private Date logStartDate;
    private Date logEndDate;
    private int planDay;
    private String userId;
    private Date createdAt;
    private Date updatedAt;
    
    private int loveCount; // 추가: 좋아요 수
    


    
    


	public LogVO() {
		super();
	}



	
	
	
	public LogVO(long logId, String imagePath, String logTitle, Date logStartDate, Date logEndDate, int planDay,
			String userId, Date createdAt, Date updatedAt, int loveCount, Integer accoId, Integer restId,
			Integer attrId, String logContent) {
		super();
		this.logId = logId;
		this.imagePath = imagePath;
		this.logTitle = logTitle;
		this.logStartDate = logStartDate;
		this.logEndDate = logEndDate;
		this.planDay = planDay;
		this.userId = userId;
		this.createdAt = createdAt;
		this.updatedAt = updatedAt;
		this.loveCount = loveCount;

	}






	public LogVO(long logId, String imagePath, String logTitle, Date logStartDate, Date logEndDate, int planDay,
			String userId, Date createdAt, Date updatedAt, int loveCount) {
		super();
		this.logId = logId;
		this.imagePath = imagePath;
		this.logTitle = logTitle;
		this.logStartDate = logStartDate;
		this.logEndDate = logEndDate;
		this.planDay = planDay;
		this.userId = userId;
		this.createdAt = createdAt;
		this.updatedAt = updatedAt;
		this.loveCount = loveCount;
	}



	public long getLogId() {
		return logId;
	}



	public void setLogId(long logId) {
		this.logId = logId;
	}



	public String getImagePath() {
		return imagePath;
	}



	public void setImagePath(String imagePath) {
		this.imagePath = imagePath;
	}



	public String getLogTitle() {
		return logTitle;
	}



	public void setLogTitle(String logTitle) {
		this.logTitle = logTitle;
	}



	public Date getLogStartDate() {
		return logStartDate;
	}



	public void setLogStartDate(Date logStartDate) {
		this.logStartDate = logStartDate;
	}



	public Date getLogEndDate() {
		return logEndDate;
	}



	public void setLogEndDate(Date logEndDate) {
		this.logEndDate = logEndDate;
	}



	public int getPlanDay() {
		return planDay;
	}



	public void setPlanDay(int planDay) {
		this.planDay = planDay;
	}



	public String getUserId() {
		return userId;
	}



	public void setUserId(String userId) {
		this.userId = userId;
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
    
	
	
    public int getLoveCount() { return loveCount; }
    public void setLoveCount(int loveCount) { this.loveCount = loveCount; }



   
    
    

	@Override
	public String toString() {
		return "LogVO [logId=" + logId + ", imagePath=" + imagePath + ", logTitle=" + logTitle + ", logStartDate="
				+ logStartDate + ", logEndDate=" + logEndDate + ", planDay=" + planDay + ", userId=" + userId
				+ ", createdAt=" + createdAt + ", updatedAt=" + updatedAt + ", loveCount=" + loveCount + "]";
	}
	
	
   
    
}
