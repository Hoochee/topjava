package ru.javawebinar.topjava.dao;

import java.time.LocalDateTime;
import java.util.List;

public interface DAO<Meal> {
    void updateMeal(int id, LocalDateTime dateTime, String description, int calories);

    void createMeal(LocalDateTime dateTime, String description, int calories);

    void delete(int id);

    List<Meal> getListOfMeals();

    Meal getMeal(int id);
}
