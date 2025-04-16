package ru.evg.mask_logback.maskUtil;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.ClassUtils;
import org.springframework.stereotype.Component;
import ru.evg.mask_logback.maskUtil.finder.MaskingField;

import java.lang.reflect.Array;
import java.lang.reflect.Field;
import java.net.URI;
import java.net.URL;
import java.util.*;

@Component
@RequiredArgsConstructor
public class MaskingProcess {

    public String maskFields(Object obj) {
        ObjectMapper objectMapper = new ObjectMapper();
        try {
            Object maskedObject = processObject(obj);
            return objectMapper.writeValueAsString(maskedObject);
        } catch (Exception e) {
            throw new RuntimeException("Failed to convert to JSON", e);
        }
    }

    private Object processObject(Object obj) throws IllegalAccessException {
        if (obj == null) {
            return null;
        }

        if (obj instanceof Number || obj instanceof Boolean || obj instanceof Character) {
            return obj;
        }

        if (obj instanceof String) {
            return obj;
        }

        if (obj instanceof Collection) {
            Collection<?> collection = (Collection<?>) obj;
            List<Object> result = new ArrayList<>();
            for (Object item : collection) {
                result.add(processObject(item));
            }
            return result;
        }

        if (obj.getClass().isArray()) {
            Object[] array = (Object[]) obj;
            Object[] result = new Object[array.length];
            for (int i = 0; i < array.length; i++) {
                result[i] = processObject(array[i]);
            }
            return result;
        }

        if (obj instanceof Map) {
            Map<?, ?> map = (Map<?, ?>) obj;
            Map<Object, Object> result = new HashMap<>();
            for (Map.Entry<?, ?> entry : map.entrySet()) {
                result.put(processObject(entry.getKey()), processObject(entry.getValue()));
            }
            return result;
        }

        Map<String, Object> result = new HashMap<>();
        Field[] fields = obj.getClass().getDeclaredFields();

        for (Field field : fields) {
            field.setAccessible(true);
            String fieldName = field.getName();
            String fieldValue = field.get(obj).toString();

            if (field.isAnnotationPresent(MaskingField.class) && fieldValue != null) {
                fieldValue = "*".repeat(fieldValue.length());
            }

            result.put(fieldName, fieldValue);
        }

        return result;
    }
}
