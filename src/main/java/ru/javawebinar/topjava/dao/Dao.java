package ru.javawebinar.topjava.dao;

import ru.javawebinar.topjava.model.Meal;

import java.time.LocalDateTime;
import java.util.List;

public interface Dao {
    void updateMeal(int id, LocalDateTime dateTime, String description, int calories);

    void createMeal(LocalDateTime dateTime, String description, int calories);

    void delete(int id);

    List<Meal> getListOfMeals();

    Meal getMeal(int id);
}
