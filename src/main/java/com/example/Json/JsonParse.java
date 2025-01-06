package com.example.Json;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.IOException;

public class JsonParse {
    private static final ObjectMapper objectMapper = new ObjectMapper();

    static {
        objectMapper.configure(com.fasterxml.jackson.databind.DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
    }

    public static <T> T parseJson(String jsonString, TypeReference<T> typeReference) {
        try {
            return objectMapper.readValue(jsonString, typeReference);
        } catch (IOException e) {
            e.printStackTrace();
            System.out.println("Error while parsing JSON: " + e.getMessage());
            return null;
        }
    }

}
