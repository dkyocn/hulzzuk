package com.hulzzuk.log.model.vo;

import java.sql.Date;
import java.util.List;

public class LogCommentVO {

	
	 	private Long commentId;     // TB_COMMENT의 COMMENT_ID 또는 TB_RECOMMENT의 COMMENT_ID (참조)
	    private Long recoId;        // TB_RECOMMENT의 RECO_ID
	    private String content;
	    private Date createdAt;
	    private Date updatedAt;
	    private Long logId;         // TB_COMMENT만 해당
	    private Long vocId;         // TB_COMMENT만 해당
	    private String userId;
	    private boolean isReply;    // 댓글인지 대댓글인지 구분용
	    private Long parentCommentId; // 대댓글일 경우 참조하는 COMMENT_ID

	    //댓글관련 추가
	    
	    private List<LogCommentVO> replies;

	 


		public LogCommentVO() {
			super();
			// TODO Auto-generated constructor stub
		}



		public LogCommentVO(Long commentId, Long recoId, String content, Date createdAt, Date updatedAt, Long logId,
				Long vocId, String userId, boolean isReply, Long parentCommentId) {
			super();
			this.commentId = commentId;
			this.recoId = recoId;
			this.content = content;
			this.createdAt = createdAt;
			this.updatedAt = updatedAt;
			this.logId = logId;
			this.vocId = vocId;
			this.userId = userId;
			this.isReply = isReply;
			this.parentCommentId = parentCommentId;
		}



		public Long getCommentId() {
			return commentId;
		}



		public void setCommentId(Long commentId) {
			this.commentId = commentId;
		}



		public Long getRecoId() {
			return recoId;
		}



		public void setRecoId(Long recoId) {
			this.recoId = recoId;
		}



		public String getContent() {
			return content;
		}



		public void setContent(String content) {
			this.content = content;
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



		public Long getLogId() {
			return logId;
		}



		public void setLogId(Long logId) {
			this.logId = logId;
		}



		public Long getVocId() {
			return vocId;
		}



		public void setVocId(Long vocId) {
			this.vocId = vocId;
		}



		public String getUserId() {
			return userId;
		}



		public void setUserId(String userId) {
			this.userId = userId;
		}



		public boolean isReply() {
			return isReply;
		}



		public void setReply(boolean isReply) {
			this.isReply = isReply;
		}



		public Long getParentCommentId() {
			return parentCommentId;
		}



		public void setParentCommentId(Long parentCommentId) {
			this.parentCommentId = parentCommentId;
		}

   		
		public List<LogCommentVO> getReplies() {
			return replies;
		}



		public void setReplies(List<LogCommentVO> replies) {
			this.replies = replies;
		}


		@Override
		public String toString() {
			return "LogCommentVO [commentId=" + commentId + ", recoId=" + recoId + ", content=" + content
					+ ", createdAt=" + createdAt + ", updatedAt=" + updatedAt + ", logId=" + logId + ", vocId=" + vocId
					+ ", userId=" + userId + ", isReply=" + isReply + ", parentCommentId=" + parentCommentId + "]";
		}

	    
	
}
