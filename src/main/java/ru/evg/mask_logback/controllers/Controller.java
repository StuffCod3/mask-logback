package ru.evg.mask_logback.controllers;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.evg.mask_logback.maskUtil.MaskProcessor;
import ru.evg.mask_logback.model.Employee;
import ru.evg.mask_logback.model.Passport;
import ru.evg.mask_logback.model.Response;
import ru.evg.mask_logback.model.Sex;
import ru.evg.mask_logback.utils.JsonUtilWrapper;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.OffsetDateTime;
import java.util.List;
import java.util.UUID;

@Slf4j
@RestController
@RequiredArgsConstructor
public class Controller {

    private final JsonUtilWrapper jsonUtilWrapper;
    private final MaskProcessor mask;

    @GetMapping("/test")
    public Response test(){
        Response response = mockResponse(List.of(mockFirstEmployee(), mockSecondEmployee(), mockThirdEmployee()));
        log.info("Ответ: {}", mask.mask(response));

        return response;
    }
    private Response mockResponse(List<Employee> employees){
        return new Response().id(UUID.randomUUID()).employee(employees);
    }

    private Employee mockFirstEmployee(){
        Employee employee = new Employee().id(UUID.randomUUID());
        Passport passport = new Passport();

        passport.series(BigDecimal.valueOf(1234));
        passport.number(BigDecimal.valueOf(987654321));
        passport.birthDate(LocalDate.now());
        passport.sex(Sex.MALE);
        passport.placeOfIssue("Место выдачи");

        employee.login("login");
        employee.lastActive(OffsetDateTime.now());
        employee.passport(passport);

        return employee;
    }

    private Employee mockSecondEmployee(){
        Employee employee = new Employee().id(UUID.randomUUID());
        Passport passport = new Passport();

        passport.series(BigDecimal.valueOf(1111));
        passport.number(BigDecimal.valueOf(1111111111));
        passport.birthDate(LocalDate.now());
        passport.sex(Sex.FEMALE);
        passport.placeOfIssue("Обычное место выдачи");

        employee.login("login_female");
        employee.lastActive(OffsetDateTime.now());
        employee.passport(passport);

        return employee;
    }

    private Employee mockThirdEmployee(){
        Employee employee = new Employee().id(UUID.randomUUID());
        Passport passport = new Passport();

        passport.series(BigDecimal.valueOf(2222));
        passport.number(BigDecimal.valueOf(222222222));
        passport.birthDate(LocalDate.now());
        passport.sex(Sex.MALE);
        passport.placeOfIssue("Необычное место выдачи");

        employee.login("login_male_boss");
        employee.lastActive(OffsetDateTime.now());
        employee.passport(passport);

        return employee;
    }
}
