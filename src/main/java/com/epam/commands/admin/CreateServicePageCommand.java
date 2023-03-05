package com.epam.commands.admin;

import com.epam.commands.ServletCommand;
import com.epam.dao.Master.MasterDAO;
import com.epam.dao.Record.RecordDAO;
import com.epam.dao.Service.ServiceDAO;
import com.epam.dao.ServiceMaster.ServiceMasterDAO;
import com.epam.dao.User.UserDAO;
import com.epam.service.*;
import com.epam.utility.ParsePathProperties;
import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.LogManager;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Class that show creation-service page
 *
 * @author Volodymyr Tytukh
 */

public class CreateServicePageCommand implements ServletCommand {

    private static final Logger LOGGER = LogManager.getLogger(CreateServicePageCommand.class);
    private ServiceService service;
    private ServiceDAO serviceDAO;
    private MasterService master;
    private MasterDAO masterDAO;
    private ServiceMasterService serviceMaster;
    private ServiceMasterDAO serviceMasterDAO;
    private RecordService record;
    private RecordDAO recordDAO;
    private UserService user;
    private UserDAO userDAO;

    private static String page;
    private static String createServicePage;


    public CreateServicePageCommand() {
        LOGGER.info("Initializing CreateServicePageCommand");

        userDAO = UserDAO.getInstance();
        user = new UserService(userDAO);
        serviceDAO = ServiceDAO.getInstance();
        service = new ServiceService(serviceDAO);
        masterDAO = MasterDAO.getInstance();
        master = new MasterService(masterDAO);
        serviceMasterDAO = ServiceMasterDAO.getInstance();
        serviceMaster = new ServiceMasterService(serviceMasterDAO);
        recordDAO = RecordDAO.getInstance();
        record = new RecordService(recordDAO);

        ParsePathProperties properties = ParsePathProperties.getInstance();
        page = properties.getProperty("mainPage");
        createServicePage = properties.getProperty("createServicePage");
    }

    public String execute(HttpServletRequest request, HttpServletResponse response) throws IOException {
        LOGGER.info("Executing CreateServicePageCommand");

        String resultPage = createServicePage;
//        if(request.getParameter("fname") == null && request.getParameter("lname") == null &&
//                request.getParameter("email") == null && request.getParameter("password") == null &&
//                request.getParameter("address") == null) {
//            LOGGER.info("Returning creation service page");
//            return resultPage;
//        }


        //resultPage = page;
        return resultPage;
    }
}
