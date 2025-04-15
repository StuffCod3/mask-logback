package ru.evg.mask_logback.utils;

import org.springframework.stereotype.Component;

@Component
public class JsonUtilWrapper {

    public String toJson(Object obj) {
        return LoggerUtil.toJson(obj);
    }
}
