package com.hulzzuk.checklist.model.vo;

import java.io.Serializable;

public class ChecklistVO implements Serializable {

    private long checkId;
    private String checkTitle;
    private String checkContent;
    private boolean checkYN;
    private long planId;

    public ChecklistVO() {}

    public ChecklistVO(long checkId, String checkTitle, String checkContent, boolean checkYN, long planId) {
        this.checkId = checkId;
        this.checkTitle = checkTitle;
        this.checkContent = checkContent;
        this.checkYN = checkYN;
        this.planId = planId;
    }

    public long getCheckId() {
        return checkId;
    }

    public void setCheckId(long checkId) {
        this.checkId = checkId;
    }

    public String getCheckTitle() {
        return checkTitle;
    }

    public void setCheckTitle(String checkTitle) {
        this.checkTitle = checkTitle;
    }

    public String getCheckContent() {
        return checkContent;
    }

    public void setCheckContent(String checkContent) {
        this.checkContent = checkContent;
    }

    public boolean isCheckYN() {
        return checkYN;
    }

    public void setCheckYN(boolean checkYN) {
        this.checkYN = checkYN;
    }

    public long getPlanId() {
        return planId;
    }

    public void setPlanId(long planId) {
        this.planId = planId;
    }
}
