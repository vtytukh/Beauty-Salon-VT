package com.epam.commands.admin;

import com.epam.commands.ServletCommand;
import com.epam.dao.Master.MasterDAO;
import com.epam.dao.ServiceMaster.ServiceMasterDAO;
import com.epam.model.Master;
import com.epam.model.ServiceMaster;
import com.epam.service.FieldValidationService;
import com.epam.service.MasterService;
import com.epam.service.ServiceMasterService;
import com.epam.utility.ParsePathProperties;
import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.LogManager;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.math.BigDecimal;
import java.math.RoundingMode;

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

        String idString = request.getParameter("id");
        String priceString = request.getParameter("price");
        String serviceIdString = request.getParameter("service-id");

        if (idString != null && serviceIdString != null && FieldValidationService.isMoneyValid(priceString)) {
            long id = Integer.parseInt(idString);
            BigDecimal price = new BigDecimal(priceString).setScale(2, RoundingMode.HALF_UP);
            long serviceId = Integer.parseInt(serviceIdString);
            LOGGER.info("user id = {}", id);
            Master mas = master.findMasterByUserId(id);
            LOGGER.info("master id = {}", mas.getId());
            LOGGER.info("service id = {}", serviceId);
            ServiceMaster sm = new ServiceMaster();
            sm.setMaster_id(mas.getId());
            sm.setService_id(serviceId);
            sm.setPrice(price);

            if (serviceMaster.addServiceMaster(sm)) {
                LOGGER.info("Added service to master successfully");
                response.sendRedirect(request.getContextPath()+"/admin/users?valid_message=service_added_success");
            } else {
                LOGGER.info("Added service to master unsuccessfully");
                response.sendRedirect(request.getContextPath()+"/admin/users?valid_message=service_added_unsuccessful");
            }
            return null;
        } else  {
            LOGGER.info("Parameters are null");
            return pageRe;
        }
    }
}
