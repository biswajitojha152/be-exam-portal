package com.examportal.dto;

import java.util.List;

public class UpdateCategoriesStatusDTO {
    private List<Integer> ids;
    private boolean isActive;

    public UpdateCategoriesStatusDTO() {
    }

    public UpdateCategoriesStatusDTO(List<Integer> ids, boolean isActive) {
        this.ids = ids;
        this.isActive = isActive;
    }

    public List<Integer> getIds() {
        return ids;
    }

    public void setIds(List<Integer> ids) {
        this.ids = ids;
    }

    public boolean getIsActive() {
        return isActive;
    }

    public void setIsActive(boolean isActive) {
        this.isActive = isActive;
    }
}
