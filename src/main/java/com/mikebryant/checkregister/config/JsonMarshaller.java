package com.mikebryant.checkregister.config;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.type.CollectionType;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@Component
@Slf4j
public class JsonMarshaller {
    @Autowired
    private ObjectMapper objectMapper;


    public String marshal(Object object) {
        String json = "";

        try {
            json = objectMapper.writeValueAsString(object);
        } catch (JsonProcessingException e) {
            log.warn(e.getMessage(), e);
        }

        return json;
    }

    public Object unmarshal(String json, Class<?> clazz) {
        Object object = null;

        try {
            object = objectMapper.readValue(json, clazz);
        } catch (IOException e) {
            log.warn(e.getMessage(), e);
        }

        return object;
    }

    List<Object> unmarshalList(String json, Class<?> clazz) {
        List<Object> objects = new ArrayList<>();

        CollectionType type = objectMapper.getTypeFactory().constructCollectionType(List.class, clazz);
        try {
            objects = objectMapper.readValue(json, type);
        } catch (IOException e) {
            log.warn(e.getMessage(), e);
        }

        return objects;
    }

}
