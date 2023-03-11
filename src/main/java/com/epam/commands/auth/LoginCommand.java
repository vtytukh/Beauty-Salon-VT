package com.epam.commands.auth;

import com.epam.commands.ServletCommand;
import com.epam.dao.User.UserDAO;
import com.epam.model.User;
import com.epam.service.FieldValidationService;
import com.epam.service.UserService;
import com.epam.utility.ParsePathProperties;
import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.LogManager;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

/**
 * Class that login a user
 *
 * @author Volodymyr Tytukh
 */

public class LoginCommand implements ServletCommand {

    private static final Logger LOGGER = LogManager.getLogger(LoginCommand.class);
    private static UserDAO dao;
    private static UserService userService;

    private static String loginPage;
    private static String mainPage;

    public LoginCommand() {
        userService = new UserService(UserDAO.getInstance());
        dao = UserDAO.getInstance();
        ParsePathProperties properties = ParsePathProperties.getInstance();
        mainPage = properties.getProperty("mainPage");
    }

    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) throws IOException {
        LOGGER.info("Executing LoginCommand");

        String resultPage;

        String email = request.getParameter("email");
        String password = request.getParameter("password");

        if (FieldValidationService.isMailValid(email) && FieldValidationService.isPasswordValid(password)) {
            User user = userService.getUserByCredentials(email, password);

            if (user != null) {
                LOGGER.info("User found");
                HttpSession session = request.getSession();
                session.setAttribute("id", user.getId());
                session.setAttribute("email", user.getEmail());
                session.setAttribute("username", user.getFirstName() + " " + user.getLastName());
                session.setAttribute("authenticated", true);
                session.setAttribute("role", user.getRole().value());

                resultPage = mainPage;
            } else {
                LOGGER.info("No user found");
                request.setAttribute("loginSuccess", false);
                response.sendRedirect(request.getContextPath()+"/login?valid_message=no_such_user");

                resultPage = null;
            }
        } else {
            LOGGER.info("Incorrect email or password");
            request.setAttribute("loginSuccess", false);
            response.sendRedirect(request.getContextPath()+"/login?valid_message=invalid_inputs");

            resultPage = null;
        }

        return resultPage;
    }
}
