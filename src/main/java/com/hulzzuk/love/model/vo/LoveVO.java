package com.hulzzuk.love.model.vo;

public class LoveVO implements java.io.Serializable {
	private static final long serialVersionUID = -5874601442203424373L;
	
	private long loveId;		// LOVE_ID	NUMBER
	private String accoId;		// ACCO_ID	VARCHAR2(255 BYTE)
	private String restId;		// REST_ID	VARCHAR2(255 BYTE)
	private String attrId;		// ATTR_ID	VARCHAR2(255 BYTE)
	private long logId;			// LOG_ID	NUMBER
	private String userId;		// USER_ID	VARCHAR2(30 BYTE)
	
	public LoveVO() {
		super();
	}


	public LoveVO(long loveId, String accoId, String restId, String attrId, long logId, String userId) {
		super();
		this.loveId = loveId;
		this.accoId = accoId;
		this.restId = restId;
		this.attrId = attrId;
		this.logId = logId;
		this.userId = userId;
	}

	public long getLoveId() {
		return loveId;
	}

	public void setLoveId(long loveId) {
		this.loveId = loveId;
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

	public long getLogId() {
		return logId;
	}

	public void setLogId(long logId) {
		this.logId = logId;
	}

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}
	
	
}
