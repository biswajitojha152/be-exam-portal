package com.examportal.dto;

import com.examportal.models.EOperation;

import java.time.Instant;

public class AuditLogDTO<T> {
    private Long id;
    private EOperation actionType;
    private T data;
    private String updatedBy;
    private Instant date;

    public AuditLogDTO() {
    }

    public AuditLogDTO(Long id, EOperation actionType, T data, String updatedBy, Instant date) {
        this.id = id;
        this.actionType = actionType;
        this.data = data;
        this.updatedBy = updatedBy;
        this.date = date;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public EOperation getActionType() {
        return actionType;
    }

    public void setActionType(EOperation actionType) {
        this.actionType = actionType;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }

    public String getUpdatedBy() {
        return updatedBy;
    }

    public void setUpdatedBy(String updatedBy) {
        this.updatedBy = updatedBy;
    }

    public Instant getDate() {
        return date;
    }

    public void setDate(Instant date) {
        this.date = date;
    }
}
