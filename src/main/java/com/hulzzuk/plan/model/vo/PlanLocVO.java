package com.hulzzuk.plan.model.vo;

public class PlanLocVO {
    private long planId;
    private String accoId;
    private String attrId;
    private String restId;
    private int seq;
    private int planDay;

    public PlanLocVO(long planId, String accoId, String attrId, String restId, int seq, int planDay) {
        this.planId = planId;
        this.accoId = accoId;
        this.attrId = attrId;
        this.restId = restId;
        this.seq = seq;
        this.planDay = planDay;
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

    public String getAttrId() {
        return attrId;
    }

    public void setAttrId(String attrId) {
        this.attrId = attrId;
    }

    public String getRestId() {
        return restId;
    }

    public void setRestId(String restId) {
        this.restId = restId;
    }

    public int getseq() {
        return seq;
    }

    public void setseq(int seq) {
        this.seq = seq;
    }

    public int getPlanDay() {
        return planDay;
    }

    public void setPlanDay(int planDay) {
        this.planDay = planDay;
    }
}
