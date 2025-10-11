package com.examportal.services;

import com.examportal.dto.AuditLogDTO;
import com.examportal.models.EOperation;
import com.examportal.models.User;

import java.util.List;

public interface AuditLogService {
    void saveAuditLog(EOperation actionType, Object data);

    List<AuditLogDTO<String>> getAuditLogByEntity(String entityName, Long entityId);

}
