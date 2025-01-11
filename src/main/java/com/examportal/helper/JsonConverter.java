package com.examportal.helper;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class JsonConverter {
    @Autowired
    private ObjectMapper objectMapper;

    public <T> T convertToObject(String jsonString, Class<T> targetType){
        try{
               return objectMapper.readValue(jsonString, targetType);
        } catch (JsonProcessingException e) {
            throw new RuntimeException("Error converting JSON string to object: "+e.getMessage());
        }
    }

    public <T> List<T> convertToListOfObject(String jsonString, Class<T> targetType){
        try{
            return objectMapper.readValue(jsonString, objectMapper.getTypeFactory().constructCollectionType(List.class, targetType));
        }catch (JsonProcessingException e){
            throw new RuntimeException("Error converting List of JSON string to object: "+e.getMessage());
        }
    }
}
