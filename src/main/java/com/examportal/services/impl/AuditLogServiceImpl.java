package com.examportal.services.impl;

import com.examportal.dto.AuditLogDTO;
import com.examportal.helper.JsonConverter;
import com.examportal.models.AuditLog;
import com.examportal.models.EOperation;
import com.examportal.repository.AuditLogRepository;
import com.examportal.repository.UserRepository;
import com.examportal.services.AuditLogService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.sql.Timestamp;
import java.time.Instant;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class AuditLogServiceImpl implements AuditLogService {
    @Autowired
    private AuditLogRepository auditLogRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private JsonConverter jsonConverter;

    private static final Logger log = LoggerFactory.getLogger(AuditLogServiceImpl.class);


    @Override
    public void saveAuditLog(EOperation actionType, Object data) {

        try{
            String entityName =  data instanceof List<?> ? ((List<?>) data).get(0).getClass().getSimpleName() : data.getClass().getSimpleName();

            Long primaryKeyOfEntity = data instanceof List<?> ?  0 : getPrimaryKey(data);

            auditLogRepository.save(new AuditLog(
                            null,
                            entityName,
                            primaryKeyOfEntity,actionType,
                            objectMapper.writeValueAsString(data),
                            Timestamp.from(Instant.now()),
                            userRepository.findByUsername(
                                            SecurityContextHolder.getContext().getAuthentication().getName())
                                    .orElseThrow(()-> new UsernameNotFoundException("Username not found."))
                    )
            );
        }catch (JsonProcessingException e){
            log.error(e.getMessage());
        }
    }

    @Override
    public List<AuditLogDTO<String>> getAuditLogByEntity(String entityName, Long entityId) {
        Specification<AuditLog> auditLogSpecification = ((root, query, criteriaBuilder) -> criteriaBuilder.and(
                criteriaBuilder.equal(root.get("entityName"), entityName),
                criteriaBuilder.equal(root.get("entityId"), entityId)
        ));

        List<AuditLogDTO<String>> auditLogDTOList = new ArrayList<>();
        auditLogRepository.findAll(auditLogSpecification, Sort.by(Sort.Direction.DESC, "id")).forEach(auditLog -> {
            auditLogDTOList.add(new AuditLogDTO<>(
                    auditLog.getId(),
                    auditLog.getActionType(),
                    auditLog.getData(),
                    auditLog.getUpdatedBy().getUsername(),
                    auditLog.getTimestamp().toInstant()
            ));
        });
        return auditLogDTOList;
    }

    private Long getPrimaryKey(Object entity) {
       try{
           Method idGetter = entity.getClass().getMethod("getId");
           Object idObj = idGetter.invoke(entity);
           return idObj instanceof Long ? (Long) idObj : Long.valueOf((Integer) idObj);
       }catch (NoSuchMethodException | IllegalAccessException | InvocationTargetException e){
           throw new RuntimeException("Error getting primary key:  "+e.getMessage());
       }
    }

}
