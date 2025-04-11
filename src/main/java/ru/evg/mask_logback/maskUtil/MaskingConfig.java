package ru.evg.mask_logback.maskUtil;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

public class MaskingConfig {

    public static List<String> getMaskPatterns() {
        List<String> patterns = new ArrayList<>();
        try (InputStream inputStream = MaskingConfig.class.getResourceAsStream("/mask-patterns.txt");
             BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream))) {
            String line;
            while ((line = reader.readLine()) != null) {
                // Удаляем комментарии (всё после #)
                line = line.split("#")[0].trim();
                // Игнорируем пустые строки
                if (!line.isEmpty()) {
                    patterns.add(line);
                }
            }
        } catch (IOException e) {
            e.printStackTrace(); // Обработка ошибок
        }
        return patterns;
    }
}
