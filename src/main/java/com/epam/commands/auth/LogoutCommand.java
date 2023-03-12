package com.epam.commands.auth;

import com.epam.commands.ServletCommand;
import com.epam.dao.Master.MasterDAO;
import com.epam.dao.Service.ServiceDAO;
import com.epam.service.MasterService;
import com.epam.service.ServiceService;
import com.epam.utility.ParsePathProperties;
import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.LogManager;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Class that logout a user
 *
 * @author Volodymyr Tytukh
 */

public class LogoutCommand implements ServletCommand {
    private static final Logger LOGGER = LogManager.getLogger(LogoutCommand.class);

    private ServiceService service;
    private ServiceDAO serviceDAO;
    private MasterService master;
    private MasterDAO masterDAO;

    private static String mainPage;

    public LogoutCommand() {
        LOGGER.info("Initializing LogoutCommand");

        serviceDAO = ServiceDAO.getInstance();
        service = new ServiceService(serviceDAO);
        masterDAO = MasterDAO.getInstance();
        master = new MasterService(masterDAO);

        ParsePathProperties properties = ParsePathProperties.getInstance();
        mainPage = properties.getProperty("mainPage");
    }

    public String execute(HttpServletRequest request, HttpServletResponse response) {
        LOGGER.info("Executing LogoutCommand");
        LOGGER.info("Logging out user {}", request.getSession().getAttribute("email"));

        request.getSession().invalidate();

        request.setAttribute("services", service.findAll());
        request.setAttribute("masters", master.findAllWithName());

        return mainPage;
    }
}
