package ru.evg.mask_logback.controllers;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.evg.mask_logback.model.Order;
import ru.evg.mask_logback.model.OrderResponse;
import ru.evg.mask_logback.model.User;
import ru.evg.mask_logback.utils.JsonUtilWrapper;

import java.util.List;

@Slf4j
@RestController
@RequiredArgsConstructor
public class Controller {

    private final JsonUtilWrapper jsonUtilWrapper;

    @GetMapping("/test")
    public OrderResponse test(){
        Order order = new Order();
        order.setBisId(List.of("Test1ID", "Test2ID"));
        order.setId("test");
        order.setType("test");
        order.setUser(List.of(new User().name("User 1").login("user1").post("post1").number("12345678").series("4321"),
                new User().name("User 2").login("user2").post("post2").number("87654321").series("1234")));

        OrderResponse response = new OrderResponse().orders(List.of(order));
        log.info("Ответ: {}", jsonUtilWrapper.toJson(response));
        return response;
    }
}
