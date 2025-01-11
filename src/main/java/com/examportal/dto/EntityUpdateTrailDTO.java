package com.examportal.dto;

import java.time.Instant;
import java.util.List;

public class EntityUpdateTrailDTO<T> {
   private T actualEntity;
   private String createdBy;
   private Instant createdAt;
   private List<AuditLogDTO<T>> auditLogDTOList;

    public EntityUpdateTrailDTO() {
    }

    public EntityUpdateTrailDTO(T actualEntity, String createdBy, Instant createdAt, List<AuditLogDTO<T>> auditLogDTOList) {
        this.actualEntity = actualEntity;
        this.createdBy = createdBy;
        this.createdAt = createdAt;
        this.auditLogDTOList = auditLogDTOList;
    }

    public T getActualEntity() {
        return actualEntity;
    }

    public void setActualEntity(T actualEntity) {
        this.actualEntity = actualEntity;
    }

    public String getCreatedBy() {
        return createdBy;
    }

    public void setCreatedBy(String createdBy) {
        this.createdBy = createdBy;
    }

    public Instant getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Instant createdAt) {
        this.createdAt = createdAt;
    }

    public List<AuditLogDTO<T>> getAuditLogDTOList() {
        return auditLogDTOList;
    }

    public void setAuditLogDTOList(List<AuditLogDTO<T>> auditLogDTOList) {
        this.auditLogDTOList = auditLogDTOList;
    }
}
