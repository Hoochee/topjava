package ru.javawebinar.topjava.dao;

import ru.javawebinar.topjava.model.Meal;

import java.time.LocalDateTime;
import java.time.Month;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;

public class MealDaoInMemory implements Dao {
    private static final Map<Integer, Meal> mealsBank = new ConcurrentHashMap<>();
    private static final AtomicInteger count = new AtomicInteger(0);

    static {
        int id = count.getAndIncrement();
        Meal m1 = new Meal(id, LocalDateTime.of(2015, Month.MAY, 30, 10, 0), "Завтрак", 500);
        mealsBank.put(id, m1);
        id = count.getAndIncrement();
        m1 = new Meal(id, LocalDateTime.of(2015, Month.MAY, 31, 13, 0), "Обед", 500);
        mealsBank.put(id, m1);
        id = count.getAndIncrement();
        m1 = new Meal(id, LocalDateTime.of(2015, Month.MAY, 30, 13, 0), "Обед", 1000);
        mealsBank.put(id, m1);
        id = count.getAndIncrement();
        m1 = new Meal(id, LocalDateTime.of(2015, Month.MAY, 31, 10, 0), "Завтрак", 1000);
        mealsBank.put(id, m1);
        id = count.getAndIncrement();
        m1 = new Meal(id, LocalDateTime.of(2015, Month.MAY, 30, 20, 0), "Ужин", 500);
        mealsBank.put(id, m1);
        id = count.getAndIncrement();
        m1 = new Meal(id, LocalDateTime.of(2015, Month.MAY, 31, 20, 0), "Ужин", 510);
        mealsBank.put(id, m1);
    }

    @Override
    public void updateMeal(int id, LocalDateTime dateTime, String description, int calories) {

        mealsBank.computeIfPresent(id,(a, b)-> b=new Meal(id,dateTime,description,calories));
    }

    @Override
    public void createMeal(LocalDateTime dateTime, String description, int calories) {
        int id = count.getAndIncrement();
        Meal m1 = new Meal(id, dateTime, description, calories);
        mealsBank.put(id, m1);
    }

    @Override
    public void delete(int id) {
        mealsBank.remove(id);
    }

    @Override
    public Meal getMeal(int id) {
        return mealsBank.get(id);
    }

    @Override
    public List<Meal> getListOfMeals() {
        return new ArrayList<>(mealsBank.values());
    }
}
