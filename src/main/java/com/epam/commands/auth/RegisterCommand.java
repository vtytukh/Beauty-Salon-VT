package com.epam.commands.auth;

import com.epam.commands.ServletCommand;
import com.epam.dao.Master.MasterDAO;
import com.epam.dao.Service.ServiceDAO;
import com.epam.dao.User.UserDAO;
import com.epam.model.Role;
import com.epam.model.User;
import com.epam.model.UserBuilder;
import com.epam.service.FieldValidationService;
import com.epam.service.MasterService;
import com.epam.service.ServiceService;
import com.epam.service.UserService;
import com.epam.utility.ParsePathProperties;
import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.LogManager;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Class that register a user
 *
 * @author Volodymyr Tytukh
 */

public class RegisterCommand implements ServletCommand {
    private static final Logger LOGGER = LogManager.getLogger(RegisterCommand.class);
    private static UserDAO dao;
    private static UserService userService;

    private static String registerPage;
    private static String mainPage;

    private ServiceService service;
    private ServiceDAO serviceDAO;
    private MasterService master;
    private MasterDAO masterDAO;

    public RegisterCommand() {
        userService = new UserService(UserDAO.getInstance());
        dao = UserDAO.getInstance();
        serviceDAO = ServiceDAO.getInstance();
        service = new ServiceService(serviceDAO);
        masterDAO = MasterDAO.getInstance();
        master = new MasterService(masterDAO);

        ParsePathProperties properties = ParsePathProperties.getInstance();
        registerPage = properties.getProperty("registerPage");
        mainPage = properties.getProperty("mainPage");
    }

    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) throws IOException {
        LOGGER.info("Executing RegisterCommand");

        String resultPage = registerPage;

        String firstName = request.getParameter("firstName").trim();
        String lastName = request.getParameter("lastName").trim();
        String email = request.getParameter("email").trim();
        String password = request.getParameter("password").trim();

        if (FieldValidationService.isNameValid(firstName) && FieldValidationService.isNameValid(lastName) &&
                FieldValidationService.isMailValid(email) && FieldValidationService.isPasswordValid(password)) {

            if (userService.checkEmailAvailability(email)) {

                LOGGER.info("New user registration");

                User user = new UserBuilder().setFirstName(firstName)
                        .setLastName(lastName)
                        .setEmail(email)
                        .setPassword(password)
                        .setUserType(Role.CLIENT)
                        .build();

                //dao.createUser(user);
                if (userService.registerUser(user)) {
                    request.setAttribute("services", service.findAll());
                    request.setAttribute("masters", master.findAllWithName());
                    response.sendRedirect(request.getContextPath()+"/login?valid_message=register_success");

                    resultPage = null;
                }
            } else {
                LOGGER.info("Current email is already registered");
                response.sendRedirect(request.getContextPath()+"/register?valid_message=invalid_email");

                resultPage = null;
            }
        } else {
            LOGGER.info("Incorrect registration inputs");
            response.sendRedirect(request.getContextPath()+"/register?valid_message=invalid_inputs");

            resultPage = null;
        }

        return resultPage;
    }
}
