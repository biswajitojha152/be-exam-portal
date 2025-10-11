package com.examportal.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;

import java.util.List;

public class UpdateEntitiesStatusDTO<T> {
    @NotEmpty
    private List<T> ids;
    private boolean isActive;
    @NotBlank
    private String remark;

    public UpdateEntitiesStatusDTO() {
    }

    public UpdateEntitiesStatusDTO(List<T> ids, boolean isActive, String remark) {
        this.ids = ids;
        this.isActive = isActive;
        this.remark = remark;
    }

    public List<T> getIds() {
        return ids;
    }

    public void setIds(List<T> ids) {
        this.ids = ids;
    }

    public boolean getIsActive() {
        return isActive;
    }

    public void setIsActive(boolean isActive) {
        this.isActive = isActive;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }
}
