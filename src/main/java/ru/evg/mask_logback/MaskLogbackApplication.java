package ru.evg.mask_logback;

import lombok.extern.slf4j.Slf4j;
import org.json.JSONObject;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.util.HashMap;
import java.util.Map;

@SpringBootApplication
public class MaskLogbackApplication {

	public static void main(String[] args) {
		SpringApplication.run(MaskLogbackApplication.class, args);
	}

}
