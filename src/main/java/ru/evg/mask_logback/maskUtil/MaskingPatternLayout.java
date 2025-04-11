package ru.evg.mask_logback.maskUtil;

import ch.qos.logback.classic.PatternLayout;
import ch.qos.logback.classic.spi.ILoggingEvent;
import lombok.extern.slf4j.Slf4j;

import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.IntStream;

@Slf4j
public class MaskingPatternLayout extends PatternLayout {

    private Pattern multilinePattern;

    public MaskingPatternLayout() {
        List<String> maskPatterns = MaskingConfig.getMaskPatterns();
        log.info("Загружен список паттернов: {}", maskPatterns);
        multilinePattern = Pattern.compile(String.join("|", maskPatterns));
    }

    @Override
    public String doLayout(ILoggingEvent event) {
        return maskMessage(super.doLayout(event));
    }

    private String maskMessage(String message) {
        if (multilinePattern == null) {
            return message;
        }
        StringBuilder sb = new StringBuilder(message);
        Matcher matcher = multilinePattern.matcher(sb);
        while (matcher.find()) {
            log.info("Matched: {}", matcher.group(0)); // Для отладки
            IntStream.rangeClosed(1, matcher.groupCount()).forEach(group -> {
                if (matcher.group(group) != null) {
                    IntStream.range(matcher.start(group), matcher.end(group)).forEach(i -> sb.setCharAt(i, '*'));
                }
            });
        }
        return sb.toString();
    }
}

