package com.epam.commands.auth;

import com.epam.commands.ServletCommand;
import com.epam.dao.User.UserDAO;
import com.epam.model.User;
import com.epam.service.UserService;
import com.epam.utility.ParsePathProperties;
import org.apache.log4j.Logger;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 * Class that login a user
 *
 * @author Volodymyr Tytukh
 */

public class LoginCommand implements ServletCommand {

    private static final Logger LOGGER = Logger.getLogger(LoginCommand.class);
    private static UserDAO dao;
    private static UserService userService;

    private static String loginPage;
    private static String mainPage;

    public LoginCommand() {
        userService = new UserService(UserDAO.getInstance());
        dao = UserDAO.getInstance();
        ParsePathProperties properties = ParsePathProperties.getInstance();
        loginPage = properties.getProperty("loginPage");
        mainPage = properties.getProperty("mainPage");
    }

    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) {
        LOGGER.info("Executing LoginCommand");

        String resultPage = loginPage;

        if (request.getParameter("email") != null && request.getParameter("password") != null) {
            User user = userService.getUserByCredentials
                    (request.getParameter("email"),
                            request.getParameter("password"));

            if (user != null) {
                HttpSession session = request.getSession();
                session.setAttribute("id", user.getId());
                session.setAttribute("email", user.getEmail());
                session.setAttribute("username", user.getFirstName() + " " + user.getLastName());
                session.setAttribute("authenticated", true);
                session.setAttribute("role", user.getRole().value());

                resultPage = mainPage;
            } else {
                request.setAttribute("loginSuccess", false);
            }
        }

        return resultPage;
    }
}
