package ru.javawebinar.topjava.web.meal;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.service.MealService;
import ru.javawebinar.topjava.util.MealsUtil;
import ru.javawebinar.topjava.web.SecurityUtil;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.Objects;

@Controller
public class MealRestController {
    protected final Logger log = LoggerFactory.getLogger(getClass());
    @Autowired
    private final MealService service;

    public MealRestController(MealService service) {
        this.service = service;
    }

    private int getId(HttpServletRequest request) {
        String paramId = Objects.requireNonNull(request.getParameter("id"));
        return Integer.parseInt(paramId);
    }

    public void save(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        int id = getId(request);
        log.info("save {}", id);
        String action = request.getParameter("action");
        final Meal meal = "create".equals(action) ?
                new Meal(LocalDateTime.now().truncatedTo(ChronoUnit.MINUTES), "", 1000) :
                service.get(id, SecurityUtil.authUserId());
        request.setAttribute("meal", meal);
        request.getRequestDispatcher("/mealForm.jsp").forward(request, response);
    }

    public void delete(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        int id = getId(request);
        log.info("Delete {}", id);
        service.delete(id, SecurityUtil.authUserId());
        response.sendRedirect("meals");
    }

    public void getAll(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        log.info("getAll");
        request.setAttribute("meals",
                MealsUtil.getTos(service.getAll(SecurityUtil.authUserId()), MealsUtil.DEFAULT_CALORIES_PER_DAY));
        request.getRequestDispatcher("/meals.jsp").forward(request, response);
    }

    public void getAllWithFilter(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

    }

    public void doPostController(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.setCharacterEncoding("UTF-8");
        String id = request.getParameter("id");

        Meal meal = new Meal(id.isEmpty() ? null : Integer.valueOf(id),
                LocalDateTime.parse(request.getParameter("dateTime")),
                request.getParameter("description"),
                Integer.parseInt(request.getParameter("calories")));

        log.info(meal.isNew() ? "Create {}" : "Update {}", meal);
        service.save(meal, SecurityUtil.authUserId());
        response.sendRedirect("meals");
    }

    public void doGetController(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String action = request.getParameter("action");
        switch (action == null ? "all" : action) {
            case "delete":
                delete(request, response);
                break;
            case "create":
            case "update":
                save(request, response);
                break;
            case "filter":
                getAllWithFilter(request, response);
                break;
            case "all":
            default:
                getAll(request, response);
                break;
        }
    }
}