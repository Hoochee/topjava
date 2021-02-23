package ru.javawebinar.topjava;

import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.model.User;

import java.time.LocalDateTime;
import java.time.Month;
import java.util.Arrays;

import static ru.javawebinar.topjava.model.AbstractBaseEntity.START_SEQ;
import static org.assertj.core.api.Assertions.assertThat;

public class MealsTestData {

    public static final int USER1_ID = START_SEQ;
    public static final int USER2_ID = START_SEQ + 1;
    public static final int MEAL1_ID = START_SEQ + 2;
    public static final int MEAL2_ID = START_SEQ + 3;
    public static final int MEAL3_ID = START_SEQ + 4;
    public static final int NOT_FOUND = START_SEQ + 5;

    public static final Meal meal3 = new Meal(MEAL3_ID, LocalDateTime.of(2021, Month.FEBRUARY, 20, 10, 24), "завтрак", 300);
    public static final Meal meal1 = new Meal(MEAL1_ID, LocalDateTime.of(2021, Month.FEBRUARY, 20, 17, 24), "обед", 500);
    public static final Meal meal2 = new Meal(MEAL2_ID, LocalDateTime.of(2021, Month.FEBRUARY, 20, 23, 24), "ужин", 700);

    public static Meal getNew() {
        return new Meal(null, LocalDateTime.of(2021, Month.FEBRUARY, 23, 23, 24), "new description", 10000);
    }

    public static Meal getUpdated() {
        Meal updated = new Meal(meal1);
        updated.setDateTime(LocalDateTime.of(2019, Month.FEBRUARY, 20, 17, 24));
        updated.setDescription("other");
        updated.setCalories(5000);
        return updated;
    }

    public static void assertMatch(Meal actual, Meal expected) {
        assertThat(actual).usingRecursiveComparison().isEqualTo(expected);
    }

    public static void assertMatch(Iterable<Meal> actual, Meal... expected) {
        assertMatch(actual, Arrays.asList(expected));
    }

    public static void assertMatch(Iterable<Meal> actual, Iterable<Meal> expected) {
        assertThat(actual).usingRecursiveFieldByFieldElementComparator().isEqualTo(expected);
    }
}
