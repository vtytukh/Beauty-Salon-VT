package com.epam.commands.auth;

import com.epam.commands.ServletCommand;
import com.epam.utility.ParsePathProperties;
import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.LogManager;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Class that get register page
 *
 * @author Volodymyr Tytukh
 */

public class RegisterPageCommand implements ServletCommand {

    private static final Logger LOGGER = LogManager.getLogger(RegisterPageCommand.class);

    private static String registerPage;
    private static String mainPage;

    public RegisterPageCommand() {
        LOGGER.info("Initializing RegisterPageCommand");

        ParsePathProperties properties = ParsePathProperties.getInstance();
        registerPage = properties.getProperty("registerPage");
        mainPage = properties.getProperty("mainPage");
    }

    public String execute(HttpServletRequest request, HttpServletResponse response) {
        LOGGER.info("Executing RegisterPageCommand");

        String resultPage = registerPage;

        if (request.getSession().getAttribute("authenticated") != null &&
                request.getSession().getAttribute("authenticated").equals(true)) {
            resultPage = mainPage;
        } else if (request.getParameter("fname") == null && request.getParameter("lname") == null &&
                request.getParameter("email") == null && request.getParameter("password") == null &&
                request.getParameter("address") == null) {
            LOGGER.info("Returning registration page");
            return resultPage;
        }

        return resultPage;
    }
}
