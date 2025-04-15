package ru.evg.mask_logback.maskUtil;

import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.ClassUtils;
import org.springframework.stereotype.Component;
import ru.evg.mask_logback.maskUtil.finder.MaskingField;

import java.lang.reflect.Array;
import java.lang.reflect.Field;
import java.util.Collection;

@Component
@RequiredArgsConstructor
public class MaskingProcess {

    public void generatePatternsFromFields(Object object) {
        if (object != null) {
            maskFields(object);
        }
    }

    private void maskFields(Object object) {
        // Если объект примитив, его обёртка или String – не обрабатываем
        if (object == null || ClassUtils.isPrimitiveOrWrapper(object.getClass()) || object instanceof String) {
            return;
        }

        // Если объект является коллекцией, обрабатываем каждый элемент
        if (object instanceof Collection) {
            for (Object item : (Collection<?>) object) {
                maskFields(item);
            }
            return;
        }

        // Если объект — массив, обрабатываем каждый его элемент
        if (object.getClass().isArray()) {
            int length = Array.getLength(object);
            for (int i = 0; i < length; i++) {
                Object item = Array.get(object, i);
                maskFields(item);
            }
            return;
        }

        // Обрабатываем поля объекта
        Field[] fields = object.getClass().getDeclaredFields();
        for (Field field : fields) {
            // Обязательно устанавливаем доступ только к тем полям, которые объявлены в нашем классе
            field.setAccessible(true);
            try {
                // Если поле аннотировано @MaskingField и его значение является строкой, маскируем значение
                if (field.isAnnotationPresent(MaskingField.class)) {
                    Object value = field.get(object);
                    if (value instanceof String) {
                        String maskedValue = "*".repeat(value.toString().length());
                        field.set(object, maskedValue);
                    }
                }
                // Рекурсивно обрабатываем значение поля, если оно не является примитивом/обёрткой/String
                Object fieldValue = field.get(object);
                if (fieldValue != null) {
                    maskFields(fieldValue);
                }
            } catch (IllegalAccessException e) {
                throw new RuntimeException(e);
            } finally {
                field.setAccessible(false);
            }
        }
    }
}
