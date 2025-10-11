package com.examportal.dto;

import java.time.Instant;
import java.util.List;

public class EntitiesStatusUpdateTrailDTO <T>{
    private String updatedBy;
    private Instant updatedAt;
    private List<T> affectedEntities;
    private String remark;
    private boolean isActive;

    public EntitiesStatusUpdateTrailDTO() {
    }

    public EntitiesStatusUpdateTrailDTO(String updatedBy, Instant updatedAt, List<T> affectedEntities, String remark, boolean isActive) {
        this.updatedBy = updatedBy;
        this.updatedAt = updatedAt;
        this.affectedEntities = affectedEntities;
        this.remark = remark;
        this.isActive = isActive;
    }

    public String getUpdatedBy() {
        return updatedBy;
    }

    public void setUpdatedBy(String updatedBy) {
        this.updatedBy = updatedBy;
    }

    public Instant getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(Instant updatedAt) {
        this.updatedAt = updatedAt;
    }

    public List<T> getAffectedEntities() {
        return affectedEntities;
    }

    public void setAffectedEntities(List<T> affectedEntities) {
        this.affectedEntities = affectedEntities;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public boolean getIsActive() {
        return isActive;
    }

    public void setIsActive(boolean active) {
        isActive = active;
    }
}
