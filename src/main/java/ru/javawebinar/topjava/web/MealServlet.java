package ru.javawebinar.topjava.web;

import org.slf4j.Logger;
import ru.javawebinar.topjava.dao.DAO;
import ru.javawebinar.topjava.dao.MealDAOimpl;
import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.util.MealsUtil;

import javax.servlet.Servlet;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.Collections;

import static org.slf4j.LoggerFactory.getLogger;

public class MealServlet extends HttpServlet {
    private static final Logger log = getLogger(Servlet.class);
    private static DAO<Meal> storage = new MealDAOimpl();
    private static final int CALORIES_PER_DAY = 2000;
    private static String standartpage = "/meals.jsp";
    private static String edit = "/editMealForm.jsp";
    public static final DateTimeFormatter FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");


    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
        log.debug("redirect to meal");
        req.setCharacterEncoding("UTF-8");
        String forward = "";
        String action = req.getParameter("action");
        if (action == null) {
            forward = standartpage;
            req.setAttribute("list", MealsUtil.filteredByStreams(storage.getListOfMeals(), LocalTime.MIN, LocalTime.MAX, CALORIES_PER_DAY));
        } else if (action.equalsIgnoreCase("delete")) {
            int userId = Integer.parseInt(req.getParameter("userId"));
            storage.delete(userId);
            forward = standartpage;
            req.setAttribute("list", MealsUtil.filteredByStreams(storage.getListOfMeals(), LocalTime.MIN, LocalTime.MAX, CALORIES_PER_DAY));
        } else if (action.equalsIgnoreCase("edit")) {
            forward = edit;
            int userId = Integer.parseInt(req.getParameter("userId"));
            Meal meal = storage.getMeal(userId);
            req.setAttribute("isAddUser", true);
            req.setAttribute("list", MealsUtil.filteredByStreams(Collections.singletonList(meal), LocalTime.MIN, LocalTime.MAX, CALORIES_PER_DAY));
        } else {
            forward = edit;
        }


        req.getRequestDispatcher(forward).forward(req, res);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.setCharacterEncoding("UTF-8");
        String stringID = req.getParameter("ID");
        String description = req.getParameter("nameMeal");
        int calories = Integer.parseInt(req.getParameter("calories"));
        String stringDate = req.getParameter("date");
        LocalDateTime datetime = LocalDateTime.parse(stringDate.replace("T", " "), FORMATTER);
        if (stringID != null) {
            int id = Integer.parseInt(stringID);
            storage.updateMeal(id, datetime, description, calories);
            req.setAttribute("list", MealsUtil.filteredByStreams(storage.getListOfMeals(), LocalTime.MIN, LocalTime.MAX, CALORIES_PER_DAY));
            req.getRequestDispatcher(standartpage).forward(req, resp);
        } else {
            storage.createMeal(datetime, description, calories);
            req.setAttribute("list", MealsUtil.filteredByStreams(storage.getListOfMeals(), LocalTime.MIN, LocalTime.MAX, CALORIES_PER_DAY));
            req.getRequestDispatcher(standartpage).forward(req, resp);
        }
    }
}
