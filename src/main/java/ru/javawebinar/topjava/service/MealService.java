package ru.javawebinar.topjava.service;

import org.springframework.stereotype.Service;
import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.repository.MealRepository;
import ru.javawebinar.topjava.util.ValidationUtil;
import ru.javawebinar.topjava.util.exception.NotFoundException;

import java.util.Collection;
import java.util.List;

@Service
public class MealService {

    private final MealRepository repository;

    public MealService(MealRepository repository) {
        this.repository = repository;
    }

    public Meal save(Meal meal, int userId) throws NotFoundException {
        return ValidationUtil.checkNotFoundWithId(repository.save(meal, userId), meal.getId());
    }

    public void delete(int id, int userId) throws NotFoundException {
        ValidationUtil.checkNotFoundWithId(repository.delete(id, userId), id);
    }

    public Meal get(int id, int userId) {
        return ValidationUtil.checkNotFoundWithId(repository.get(id, userId), id);
    }

    public Collection<Meal> getAll(int userId) {
        return repository.getAll(userId);
    }
}