package ru.evg.mask_logback.maskUtil.aspect;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.*;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Component;
import ru.evg.mask_logback.maskUtil.MaskingProcess;

import java.lang.reflect.Field;

@Slf4j
@Aspect
@Component
@RequiredArgsConstructor
public class MaskingAspect {

    private final MaskingProcess maskingProcess;
    private final ObjectMapper objectMapper;

    @Around("execution(public String ru.evg.mask_logback.utils.JsonUtilWrapper.toJson(*)) && args(obj)")
    public Object toJsonAspect(ProceedingJoinPoint joinPoint, Object obj) throws Throwable {
        Object originalCopy = objectMapper.readValue(objectMapper.writeValueAsString(obj), obj.getClass());
        maskingProcess.generatePatternsFromFields(obj);
        String maskedJson = (String) joinPoint.proceed(new Object[]{obj});
        BeanUtils.copyProperties(originalCopy, obj);
        return maskedJson;
    }

}
