package com.epam.commands.admin;

import com.epam.commands.ServletCommand;
import com.epam.dao.User.UserDAO;
import com.epam.model.User;
import com.epam.service.UserService;
import com.epam.utility.ParsePathProperties;
import org.apache.log4j.Logger;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Class that get user info
 *
 * @author Volodymyr Tytukh
 */

public class UsersItemPageCommand implements ServletCommand {

    private static final Logger LOGGER = Logger.getLogger(UsersItemPageCommand.class);

    private UserService user;
    private UserDAO userDAO;

    private static String page;
    private static String pageRe;

    public UsersItemPageCommand() {
        LOGGER.info("Initializing UsersItemPageCommand");

        userDAO = UserDAO.getInstance();
        user = new UserService(userDAO);

        ParsePathProperties properties = ParsePathProperties.getInstance();
        page = properties.getProperty("usersItemPage");
        pageRe = properties.getProperty("usersPage");
    }

    public String execute(HttpServletRequest request, HttpServletResponse response) throws IOException {
        LOGGER.info("Executing command");

        if (request.getParameter("id") == null) return pageRe;
        long id = Integer.parseInt(request.getParameter("id"));
        User usr = user.findUserById(id);
        if (usr == null || usr.getFirstName() == null) return pageRe;
        request.setAttribute("user", usr);

        return page;
    }
}
