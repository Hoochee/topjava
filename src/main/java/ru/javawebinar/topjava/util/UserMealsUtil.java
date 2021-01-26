package ru.javawebinar.topjava.util;

import ru.javawebinar.topjava.model.UserMeal;
import ru.javawebinar.topjava.model.UserMealWithExcess;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.Month;
import java.util.*;
import java.util.stream.Collectors;

public class UserMealsUtil {
    public static void main(String[] args) {
        List<UserMeal> meals = Arrays.asList(
                new UserMeal(LocalDateTime.of(2020, Month.JANUARY, 30, 10, 0), "Завтрак", 500),
                new UserMeal(LocalDateTime.of(2020, Month.JANUARY, 30, 13, 0), "Обед", 1000),
                new UserMeal(LocalDateTime.of(2020, Month.JANUARY, 30, 20, 0), "Ужин", 500),
                new UserMeal(LocalDateTime.of(2020, Month.JANUARY, 31, 0, 0), "Еда на граничное значение", 100),
                new UserMeal(LocalDateTime.of(2020, Month.JANUARY, 31, 10, 0), "Завтрак", 1000),
                new UserMeal(LocalDateTime.of(2020, Month.JANUARY, 31, 13, 0), "Обед", 500),
                new UserMeal(LocalDateTime.of(2020, Month.JANUARY, 31, 20, 0), "Ужин", 410)
        );

        List<UserMealWithExcess> mealsTo = filteredByCycles(meals, LocalTime.of(7, 0), LocalTime.of(11, 0), 2000);
        mealsTo.forEach(System.out::println);

        System.out.println(filteredByStreams(meals, LocalTime.of(7, 0), LocalTime.of(12, 0), 2000));
    }

    public static List<UserMealWithExcess> filteredByCycles(List<UserMeal> meals, LocalTime startTime, LocalTime endTime, int caloriesPerDay) {
        // TODO return filtered list with excess. Implement by cycles
        Map<LocalDate,Integer> groupByDate = new HashMap<>();
        for(UserMeal userMeal : meals){
            groupByDate.merge(getLocalDate(userMeal),userMeal.getCalories(),Integer::sum);
        }

        List<UserMealWithExcess> result = new ArrayList<>();
        for(UserMeal userMeal : meals){
            if(TimeUtil.isBetweenHalfOpen(userMeal.getDateTime().toLocalTime(),startTime,endTime)) {
                result.add(new UserMealWithExcess(userMeal.getDateTime(), userMeal.getDescription(), userMeal.getCalories(), groupByDate.get(userMeal.getDateTime().toLocalDate()) > caloriesPerDay));
            }
        }


        return result;
    }

    public static List<UserMealWithExcess> filteredByStreams(List<UserMeal> meals, LocalTime startTime, LocalTime endTime, int caloriesPerDay) {
        // TODO Implement by streams
       Map<LocalDate,Integer> map = meals.stream().collect(Collectors.toMap(UserMealsUtil::getLocalDate,UserMeal::getCalories,Integer::sum));
        return meals.stream()
                .filter(f-> TimeUtil.isBetweenHalfOpen(getLocalTime(f),startTime,endTime))
                .map(m -> new UserMealWithExcess(m.getDateTime(),m.getDescription(),m.getCalories(),map.get(getLocalDate(m))>caloriesPerDay))
                .collect(Collectors.toList());
    }
    private static LocalDate getLocalDate(UserMeal userMeal){
        return userMeal.getDateTime().toLocalDate();
    }
    private static LocalTime getLocalTime(UserMeal userMeal){
        return userMeal.getDateTime().toLocalTime();
    }
}
