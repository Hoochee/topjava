package ru.javawebinar.topjava.dao;

import ru.javawebinar.topjava.model.Meal;

import java.time.LocalDateTime;
import java.time.Month;
import java.util.Arrays;
import java.util.List;

public class MealDAOimpl implements DAO<Meal> {
    private MealStorage storage = new MealStorage();

    @Override
    public void updateMeal(int id, LocalDateTime dateTime, String description, int calories) {
        storage.updateMeal(id, dateTime, description, calories);
    }

    @Override
    public void createMeal(LocalDateTime dateTime, String description, int calories) {
        storage.createMeal(dateTime, description, calories);
    }

    @Override
    public void delete(int id) {
        storage.delete(id);
    }

    @Override
    public Meal getMeal(int id) {
        return storage.getMeal(id);
    }

    @Override
    public List<Meal> getListOfMeals() {
        return storage.getListOfMeals();
    }
}
