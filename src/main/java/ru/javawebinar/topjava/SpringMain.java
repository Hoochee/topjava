package ru.javawebinar.topjava;

import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.model.Role;
import ru.javawebinar.topjava.model.User;
import ru.javawebinar.topjava.repository.MealRepository;
import ru.javawebinar.topjava.repository.UserRepository;
import ru.javawebinar.topjava.web.user.AdminRestController;

import java.util.Arrays;
import java.util.Collection;
import java.util.List;

public class SpringMain {
    public static void main(String[] args) {
        // java 7 automatic resource management (ARM)
        try (ConfigurableApplicationContext appCtx = new ClassPathXmlApplicationContext("spring/spring-app.xml")) {
            System.out.println("Bean definition names: " + Arrays.toString(appCtx.getBeanDefinitionNames()));
            AdminRestController adminUserController = appCtx.getBean(AdminRestController.class);
            adminUserController.create(new User(null, "userName", "email@mail.ru", "password", Role.ADMIN));
            UserRepository userRepository = appCtx.getBean(UserRepository.class);
            MealRepository mealRepository = appCtx.getBean(MealRepository.class);
            userRepository.save(new User(null,"User1","1@1.ru","pas",Role.USER));
            userRepository.save(new User(null,"User2","2@1.ru","pas",Role.USER));
            userRepository.save(new User(null,"User3","3@1.ru","pas",Role.USER));
            userRepository.delete(4);
            List<User> list = userRepository.getAll();
            for(User user : list){
                System.out.println(user);
            }
            for(Meal meal : mealRepository.getAll(1)) {
                System.out.println(meal);
            }
            mealRepository.delete(3,1);
            for(Meal meal : mealRepository.getAll(1)) {
                System.out.println(meal);
            }
        }
    }
}
