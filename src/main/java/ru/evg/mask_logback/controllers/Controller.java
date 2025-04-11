package ru.evg.mask_logback.controllers;

import lombok.extern.slf4j.Slf4j;
import org.json.JSONObject;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.evg.mask_logback.models.Order;
import ru.evg.mask_logback.models.User;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static ru.evg.mask_logback.utils.LoggerUtil.toJson;

@Slf4j
@RestController
public class Controller {

    @GetMapping("/test")
    public Order test(){
        Order order = new Order();
        order.setId("test");
        order.setType("test");
        order.setUsers(List.of(new User("Каво", "Рыцари", "Главарь", "1234", "56789"),
                new User("Деда", "не извиняются", "перед рыцарем", "9874", "314234")));

        log.info("Ответ: {}", toJson(order));
        return order;
    }
}
