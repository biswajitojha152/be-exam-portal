package com.examportal.dto;

import java.util.List;

public class EntitiesStatusUpdateAuditDTO<T> {
    private List<T> dataList;
    private String remark;

    public EntitiesStatusUpdateAuditDTO() {
    }

    public EntitiesStatusUpdateAuditDTO(List<T> dataList, String remark) {
        this.dataList = dataList;
        this.remark = remark;
    }

    public List<T> getDataList() {
        return dataList;
    }

    public void setDataList(List<T> dataList) {
        this.dataList = dataList;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }
}
