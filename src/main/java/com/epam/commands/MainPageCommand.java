package com.epam.commands;

import com.epam.dao.Master.MasterDAO;
import com.epam.dao.Service.ServiceDAO;
import com.epam.service.MasterService;
import com.epam.service.ServiceService;
import com.epam.utility.ParsePathProperties;
import org.apache.log4j.Logger;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 * Class that get main page
 *
 * @author Volodymyr Tytukh
 */

public class MainPageCommand implements ServletCommand {

    private static final Logger LOGGER = Logger.getLogger(MainPageCommand.class);
    private ServiceService service;
    private ServiceDAO serviceDAO;
    private MasterService master;
    private MasterDAO masterDAO;
    private static String page;

    public MainPageCommand() {
        LOGGER.info("Initializing GetMainPageCommand");

        serviceDAO = ServiceDAO.getInstance();
        service = new ServiceService(serviceDAO);
        masterDAO = MasterDAO.getInstance();
        master = new MasterService(masterDAO);
        ParsePathProperties properties = ParsePathProperties.getInstance();
        page = properties.getProperty("mainPage");
    }

    public String execute(HttpServletRequest request, HttpServletResponse response) {
        LOGGER.info("Executing GetMainPageCommand");

        request.setAttribute("services", service.findAll());
        request.setAttribute("masters", master.findAllWithName());

        HttpSession session = request.getSession();
        if (session.getAttribute("locale") == null) {
            session.setAttribute("locale", "en");
        }

        /*if (request.getParameter("locale") != null) {
            String locale = request.getParameter("locale");
            switch (locale) {
                case "en":
                    request.getSession().setAttribute("locale", "en");
                    break;
                case "ua":
                    request.getSession().setAttribute("locale", "ua");
                    break;
            }
        }*/

        return page;
    }
}
