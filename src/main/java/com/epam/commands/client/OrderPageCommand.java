package com.epam.commands.client;

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
 * Class that get all services
 *
 * @author Volodymyr Tytukh
 */

public class OrderPageCommand implements ServletCommand {

    private static final Logger LOGGER = LogManager.getLogger(OrderPageCommand.class);
    private ServiceService service;
    private ServiceDAO serviceDAO;
    private MasterService master;
    private MasterDAO masterDAO;
    private static String page;

    public OrderPageCommand() {
        LOGGER.info("Initializing OrderPageCommand");

        serviceDAO = ServiceDAO.getInstance();
        service = new ServiceService(serviceDAO);
        masterDAO = MasterDAO.getInstance();
        master = new MasterService(masterDAO);

        ParsePathProperties properties = ParsePathProperties.getInstance();
        page = properties.getProperty("orderPage");
    }

    public String execute(HttpServletRequest request, HttpServletResponse response) {
        LOGGER.info("Executing OrderPageCommand");

        request.setAttribute("services", service.findAll());
        return page;
    }
}
