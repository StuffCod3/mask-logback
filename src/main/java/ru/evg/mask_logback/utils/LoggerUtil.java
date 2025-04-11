package ru.evg.mask_logback.utils;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonParser;

import java.util.Objects;
import java.util.Optional;

public class LoggerUtil {

    private static final ObjectMapper objectMapper = new ObjectMapper()
            .registerModule(new JavaTimeModule())
            .setSerializationInclusion(JsonInclude.Include.NON_NULL)
            .configure(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS, false)
            .configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false)
            .configure(SerializationFeature.FAIL_ON_EMPTY_BEANS, false);

    private static final Gson gson = new GsonBuilder().serializeNulls().setPrettyPrinting().create();

    public static String toJson(Object obj){
        try {
            return Optional.ofNullable(gson.toJson(JsonParser.parseString(objectMapper.writeValueAsString(obj)))).orElse("Пусто");
        } catch (Exception e){
            return Objects.toString(obj);
        }
    }
}
