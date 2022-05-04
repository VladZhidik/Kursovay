package by.jwd.restaurant.controller.command.impl;

import by.jwd.restaurant.constant.RequestParameters;
import by.jwd.restaurant.controller.command.Command;
import by.jwd.restaurant.entity.User;
import by.jwd.restaurant.service.exception.ServiceException;
import by.jwd.restaurant.service.ServiceProvider;
import by.jwd.restaurant.service.UserService;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

public class LogIn implements Command {

    private static final String LOGIN_EMAIL = RequestParameters.LOGIN_EMAIL;
    private static final String LOGIN_PASSWORD = RequestParameters.LOGIN_PASSWORD;
    private static final String ATTRIBUTE_USER_ID = "userId";
    private static final String ATTRIBUTE_USER_ROLE = "userRole";
    private static final String ATTRIBUTE_USER_EMAIL = "userEmail";

    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String login;
        String password;

        login = request.getParameter(LOGIN_EMAIL);
        password = request.getParameter(LOGIN_PASSWORD);

        ServiceProvider provider = ServiceProvider.getInstance();
        UserService userService = provider.getUserService();

        User user;

        try {
            user = userService.authorization(login, password);

            if (user == null) {
                response.sendRedirect("Controller?command=gotologinpage&message=wrong2");
                return;
            }

            HttpSession session = request.getSession(true);
            session.setAttribute(ATTRIBUTE_USER_ROLE, user.getRole());
            session.setAttribute(ATTRIBUTE_USER_ID, user.getId());
            session.setAttribute(ATTRIBUTE_USER_EMAIL, user.getEmail());

            response.sendRedirect("Controller?command=gotomainpage");

        } catch (ServiceException e) {
            response.sendRedirect("Controller?command=gotologinpage&message=wrong in catch");
        }
    }
}
