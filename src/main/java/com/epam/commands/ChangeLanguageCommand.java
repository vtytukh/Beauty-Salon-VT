package com.epam.commands;

import com.epam.utility.ParsePathProperties;
import org.apache.log4j.Logger;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

/**
 * Class for changing application language
 *
 * @author Volodymyr Tytukh
 */

public class ChangeLanguageCommand implements ServletCommand{

    private static final Logger LOGGER = Logger.getLogger(ChangeLanguageCommand.class);

    private static String page;

    public ChangeLanguageCommand() {
        LOGGER.info("Initializing ChangeLanguageCommand");

        ParsePathProperties properties = ParsePathProperties.getInstance();
        page = properties.getProperty("mainPage");
    }

    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) throws IOException {
        LOGGER.info("Executing ChangeLanguageCommand");

        String locale = request.getParameter("locale");
        HttpSession session = request.getSession();
        session.setAttribute("locale", locale);
        LOGGER.info("Locale: "+ locale);

        String previousURL = request.getHeader("referer");
        if (previousURL != null) {
            response.sendRedirect(previousURL);
        }

        return null;
    }
}
