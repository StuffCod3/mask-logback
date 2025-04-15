package ru.evg.mask_logback.maskUtil;

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

    public void generatePatternsFromFields(Object object) {
        if (object != null) {
            maskFields(object, new HashSet<>());
        }
    }

    private void maskFields(Object object, Set<Object> visited) {
        if (object == null ||
                visited.contains(object) ||
                ClassUtils.isPrimitiveOrWrapper(object.getClass()) ||
                object instanceof String ||
                object instanceof Number ||
                object instanceof Enum ||
                object instanceof java.util.Date ||
                object instanceof java.time.temporal.Temporal ||
                object instanceof UUID ||
                object instanceof URL ||
                object instanceof URI ||
                object.getClass().getName().startsWith("java.lang.reflect.") ||
                object.getClass().getName().startsWith("java.util.") && !(object instanceof Collection || object instanceof Map)) {
            return;
        }
        visited.add(object);

        if (object instanceof Collection) {
            for (Object item : (Collection<?>) object) {
                maskFields(item, visited);
            }
            return;
        }

        if (object.getClass().isArray()) {
            int length = Array.getLength(object);
            for (int i = 0; i < length; i++) {
                Object item = Array.get(object, i);
                maskFields(item, visited);
            }
            return;
        }

        Field[] fields = object.getClass().getDeclaredFields();
        for (Field field : fields) {
            field.setAccessible(true);
            try {
                if (field.isAnnotationPresent(MaskingField.class)) {
                    Object value = field.get(object);
                    if (value instanceof String) {
                        String maskedValue = "*".repeat(value.toString().length());
                        field.set(object, maskedValue);
                    }
                }
                Object fieldValue = field.get(object);
                if (fieldValue != null) {
                    maskFields(fieldValue, visited);
                }
            } catch (IllegalAccessException e) {
                throw new RuntimeException(e);
            } finally {
                field.setAccessible(false);
            }
        }
    }
}
