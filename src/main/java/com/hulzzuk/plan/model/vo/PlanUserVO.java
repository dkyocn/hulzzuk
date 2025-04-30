package com.hulzzuk.plan.model.vo;

import java.io.Serializable;

public class PlanUserVO implements Serializable {

    private String userId;
    private long planId;

    public PlanUserVO(String userId, long planId) {
        this.userId = userId;
        this.planId = planId;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public long getPlanId() {
        return planId;
    }

    public void setPlanId(long planId) {
        this.planId = planId;
    }
}
