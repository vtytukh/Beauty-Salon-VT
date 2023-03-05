package com.epam.commands.auth;

import com.epam.commands.ServletCommand;
import com.epam.utility.ParsePathProperties;
import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.LogManager;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Class that get login page
 *
 * @author Volodymyr Tytukh
 */

public class LoginPageCommand implements ServletCommand {
    private static final Logger LOGGER = LogManager.getLogger(LoginPageCommand.class);

    private static String loginPage;
    private static String mainPage;

    public LoginPageCommand() {
        LOGGER.info("Initializing LoginPageCommand");

        ParsePathProperties properties = ParsePathProperties.getInstance();
        loginPage = properties.getProperty("loginPage");
        mainPage = properties.getProperty("mainPage");
    }

    public String execute(HttpServletRequest request, HttpServletResponse response) {
        LOGGER.info("Executing LoginPageCommand");

        String resultPage = loginPage;

        if (request.getSession().getAttribute("authenticated") != null &&
                request.getSession().getAttribute("authenticated").equals(true)) {
            resultPage = mainPage;
        } else if (request.getParameter("email") == null && request.getParameter("password") == null) {
            LOGGER.info("Returning login page");
            return resultPage;
        }

        return resultPage;
    }
}
