package com.epam.commands.admin;

import com.epam.commands.ServletCommand;
import com.epam.dao.Master.MasterDAO;
import com.epam.dao.ServiceMaster.ServiceMasterDAO;
import com.epam.model.Master;
import com.epam.model.ServiceMaster;
import com.epam.service.MasterService;
import com.epam.service.ServiceMasterService;
import com.epam.utility.ParsePathProperties;
import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.LogManager;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.math.BigDecimal;

/**
 * Class that add new service to master
 *
 * @author Volodymyr Tytukh
 */

public class UsersCommand implements ServletCommand {

    private static final Logger LOGGER = LogManager.getLogger(UsersCommand.class);
    private MasterService master;
    private MasterDAO masterDAO;
    private ServiceMasterService serviceMaster;
    private ServiceMasterDAO serviceMasterDAO;

    private static String page;
    private static String pageRe;

    public UsersCommand() {
        LOGGER.info("Initializing UsersCommand");

        masterDAO = MasterDAO.getInstance();
        master = new MasterService(masterDAO);
        serviceMasterDAO = ServiceMasterDAO.getInstance();
        serviceMaster = new ServiceMasterService(serviceMasterDAO);

        ParsePathProperties properties = ParsePathProperties.getInstance();
        page = properties.getProperty("usersItemPage");
        pageRe = properties.getProperty("usersPage");
    }

    public String execute(HttpServletRequest request, HttpServletResponse response) throws IOException {
        LOGGER.info("Executing UsersCommand");

        if (request.getParameter("id") == null || request.getParameter("price") == null
                || request.getParameter("service-id") == null) {
            LOGGER.info("Parameters are null");
            return pageRe;
        }
        long id = Integer.parseInt(request.getParameter("id"));
        int price = Integer.parseInt(request.getParameter("price"));
        long service_id = Integer.parseInt(request.getParameter("service-id"));

        LOGGER.info("user id => " + id);
        Master mas = master.findMasterByUserId(id);
        LOGGER.info("master id => " + mas.getId());
        LOGGER.info("service id => " + service_id);
        ServiceMaster sm = new ServiceMaster();
        sm.setMaster_id(mas.getId());
        sm.setService_id(service_id);
        sm.setPrice(BigDecimal.valueOf(price));

        if (serviceMaster.addServiceMaster(sm)) {
            LOGGER.info("Added service to master successfully");
        }

        return page;
    }
}
