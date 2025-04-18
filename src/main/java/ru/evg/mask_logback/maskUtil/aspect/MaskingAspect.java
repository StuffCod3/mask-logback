package ru.evg.mask_logback.maskUtil.aspect;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import org.aspectj.lang.annotation.*;
import org.springframework.stereotype.Component;
import ru.evg.mask_logback.maskUtil.MaskProcessor;
import ru.evg.mask_logback.utils.JsonUtilWrapper;

@Slf4j
@Aspect
@Component
@RequiredArgsConstructor
public class MaskingAspect {

    private final MaskProcessor maskProcessor;
    private final JsonUtilWrapper jsonUtilWrapper;

//    @Around("execution(public String ru.evg.mask_logback.utils.JsonUtilWrapper.toJson(*)) && args(obj)")
//    public void toJsonAspect(ProceedingJoinPoint joinPoint, Object obj) throws Throwable {
//        log.info("АСПЕКТНЫЙ ВЫВОД {}", );
//    }

}
