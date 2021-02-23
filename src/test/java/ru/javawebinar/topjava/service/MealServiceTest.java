package ru.javawebinar.topjava.service;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.bridge.SLF4JBridgeHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.jdbc.SqlConfig;
import org.springframework.test.context.junit4.SpringRunner;
import ru.javawebinar.topjava.MealsTestData;
import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.util.exception.NotFoundException;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.Month;
import java.util.List;

import static org.junit.Assert.*;
import static ru.javawebinar.topjava.MealsTestData.*;
import static ru.javawebinar.topjava.UserTestData.USER_ID;

@ContextConfiguration({
        "classpath:spring/spring-app.xml",
        "classpath:spring/spring-db.xml"
})
@RunWith(SpringRunner.class)
@Sql(scripts = "classpath:db/populateDB.sql", config = @SqlConfig(encoding = "UTF-8"))
public class MealServiceTest {

    static {
        // Only for postgres driver logging
        // It uses java.util.logging and logged via jul-to-slf4j bridge
        SLF4JBridgeHandler.install();
    }

    @Autowired
    private MealService service;

    @Test
    public void get() {
        Meal meal = service.get(MEAL1_ID, USER1_ID);
        assertMatch(meal, MealsTestData.meal1);
    }

    @Test
    public void delete() {
        service.delete(MEAL1_ID, USER1_ID);
        assertThrows(NotFoundException.class, () -> service.get(MEAL1_ID, USER1_ID));
    }

    @Test
    public void getBetweenInclusive() {
        List<Meal> all = service.getBetweenInclusive(LocalDate.of(2021, Month.FEBRUARY, 20), LocalDate.of(2021, Month.FEBRUARY, 20), USER_ID);
        assertMatch(all, meal1, meal2, meal3);
    }

    @Test
    public void getAll() {
        List<Meal> all = service.getAll(USER1_ID);
        assertMatch(all, meal2, meal1, meal3);
    }

    @Test
    public void update() {
        Meal updated = getUpdated();
        service.update(updated, USER1_ID);
        assertMatch(service.get(MEAL1_ID, USER1_ID), getUpdated());
    }

    @Test
    public void create() {
        Meal created = service.create(getNew(), USER1_ID);
        Integer newId = created.getId();
        Meal newMeal = getNew();
        newMeal.setId(newId);
        assertMatch(created, newMeal);
        assertMatch(service.get(newId, USER1_ID), newMeal);
    }

    @Test
    public void deleteAnotherUserMeal() {
        assertThrows(NotFoundException.class, () -> service.delete(NOT_FOUND, USER1_ID));
    }

    @Test
    public void updateAnotherUserMeal() {
        Meal updated = getUpdated();
        assertThrows(NotFoundException.class, () -> service.update(updated, USER2_ID));
    }

    @Test
    public void getAnotherUserMeal() {
        assertThrows(NotFoundException.class, () -> service.get(NOT_FOUND, USER1_ID));
    }

    @Test
    public void duplicateDateTimeCreate() {
        Meal duplicate = new Meal(null, LocalDateTime.of(2021, Month.FEBRUARY, 20, 17, 24), "обед", 700);
        assertThrows(DataAccessException.class, () -> service.create(duplicate, USER1_ID));
    }
}