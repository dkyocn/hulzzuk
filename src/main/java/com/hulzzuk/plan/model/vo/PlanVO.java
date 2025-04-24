package com.hulzzuk.plan.model.vo;




import java.io.Serializable;
import java.sql.Date;

public class PlanVO implements Serializable {

    private long planId;
    private String planTitle;
    private String planPlace;
    private Date planStartDate;
    private Date planEndDate;

    public PlanVO() {
    }

    public PlanVO(long planId, String planTitle, String planPlace, Date planStartDate, Date planEndDate) {
        this.planId = planId;
        this.planTitle = planTitle;
        this.planPlace = planPlace;
        this.planStartDate = planStartDate;
        this.planEndDate = planEndDate;
    }

    public long getPlanId() {
        return planId;
    }

    public void setPlanId(long planId) {
        this.planId = planId;
    }

    public String getPlanTitle() {
        return planTitle;
    }

    public void setPlanTitle(String planTitle) {
        this.planTitle = planTitle;
    }

    public String getPlanPlace() {
        return planPlace;
    }

    public void setPlanPlace(String planPlace) {
        this.planPlace = planPlace;
    }

    public Date getPlanStartDate() {
        return planStartDate;
    }

    public void setPlanStartDate(Date planStartDate) {
        this.planStartDate = planStartDate;
    }

    public Date getPlanEndDate() {
        return planEndDate;
    }

    public void setPlanEndDate(Date planEndDate) {
        this.planEndDate = planEndDate;
    }
}
