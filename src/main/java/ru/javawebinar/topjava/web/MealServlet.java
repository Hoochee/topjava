package ru.javawebinar.topjava.web;

import org.slf4j.Logger;
import ru.javawebinar.topjava.dao.DAO;
import ru.javawebinar.topjava.dao.MealDAOimpl;
import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.model.MealTo;
import ru.javawebinar.topjava.util.MealsUtil;

import javax.servlet.Servlet;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.time.LocalTime;
import java.util.List;

import static org.slf4j.LoggerFactory.getLogger;

public class MealServlet extends HttpServlet {
    private static final Logger log = getLogger(Servlet.class);
    private static DAO storage = new MealDAOimpl();


    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        log.debug("redirect to meal");
        request.setCharacterEncoding("UTF-8");
        List list = MealsUtil.filteredByStreams(storage.getListOfMeals(), LocalTime.MIN, LocalTime.MAX, 2000);
        request.setAttribute("list", list);


        request.getRequestDispatcher("/meals.jsp").forward(request, response);
    }
}
