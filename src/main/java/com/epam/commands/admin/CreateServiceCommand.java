package com.epam.commands.admin;

import com.epam.commands.ServletCommand;
import com.epam.dao.Service.ServiceDAO;
import com.epam.model.Service;
import com.epam.service.ServiceService;
import com.epam.utility.ParsePathProperties;
import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.LogManager;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Class that add new service
 *
 * @author Volodymyr Tytukh
 */

public class CreateServiceCommand implements ServletCommand {

    private static final Logger LOGGER = LogManager.getLogger(CreateServiceCommand.class);
    private ServiceService service;
    private ServiceDAO serviceDAO;

    private static String page;
    private static String createServicePage;


    public CreateServiceCommand() {
        LOGGER.info("Initializing CreateServiceCommand");

        serviceDAO = ServiceDAO.getInstance();
        service = new ServiceService(serviceDAO);

        ParsePathProperties properties = ParsePathProperties.getInstance();
        page = properties.getProperty("mainPage");
        createServicePage = properties.getProperty("createServicePage");
    }

    public String execute(HttpServletRequest request, HttpServletResponse response) throws IOException {
        LOGGER.info("Executing CreateServiceCommand");

        String name = request.getParameter("name");
        String desc = request.getParameter("desc");

        if (name != null && desc != null) {
            LOGGER.info("Parameters name = {}, desc = {}", name, desc);

            if (service.addService(new Service(name, desc))) {
                LOGGER.info("Creating service was successful");
                response.sendRedirect(request.getContextPath()+"/admin/users?valid_message=new_service_success");
                return null;
            }
        }
        LOGGER.info("Creating service was unsuccessful");
        response.sendRedirect(request.getContextPath()+"/admin/createService?valid_message=new_service_unsuccessful");
        return null;
    }
}
