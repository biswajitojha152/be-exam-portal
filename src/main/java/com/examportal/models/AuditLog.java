package com.examportal.models;

import jakarta.persistence.*;

import java.sql.Timestamp;

@Entity
@Table(name = "audit_log")
public class AuditLog {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "entity_name", nullable = false)
    private String entityName;

    @Column(name = "entity_id", nullable = false)
    private Long entityId;

    @Column(name = "action_type", nullable = false)
    @Enumerated(EnumType.STRING)
    private EOperation actionType;

    @Column(name = "data", columnDefinition = "JSON")
    private String data;

    @Column(name = "time_stamp", nullable = false)
    private Timestamp timestamp;

    @ManyToOne(optional = false)
    private User updatedBy;

    public AuditLog() {
    }

    public AuditLog(Long id, String entityName, Long entityId, EOperation actionType, String data, Timestamp timestamp, User updatedBy) {
        this.id = id;
        this.entityName = entityName;
        this.entityId = entityId;
        this.actionType = actionType;
        this.data = data;
        this.timestamp = timestamp;
        this.updatedBy = updatedBy;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getEntityName() {
        return entityName;
    }

    public void setEntityName(String entityName) {
        this.entityName = entityName;
    }

    public Long getEntityId() {
        return entityId;
    }

    public void setEntityId(Long entityId) {
        this.entityId = entityId;
    }

    public EOperation getActionType() {
        return actionType;
    }

    public void setActionType(EOperation actionType) {
        this.actionType = actionType;
    }

    public String getData() {
        return data;
    }

    public void setData(String data) {
        this.data = data;
    }

    public Timestamp getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(Timestamp timestamp) {
        this.timestamp = timestamp;
    }

    public User getUpdatedBy() {
        return updatedBy;
    }

    public void setUpdatedBy(User updatedBy) {
        this.updatedBy = updatedBy;
    }

}
