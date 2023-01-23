package com.epam.commands;

import com.epam.dao.Service.ServiceDAO;
import com.epam.dao.ServiceMaster.ServiceMasterDAO;
import com.epam.model.Master;
import com.epam.model.Service;
import com.epam.model.ServiceMaster;
import com.epam.service.ServiceMasterService;
import com.epam.service.ServiceService;
import com.epam.utility.ParsePathProperties;
import org.apache.log4j.Logger;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;

/**
 * Class that filter service by master
 *
 * @author Volodymyr Tytukh
 */

public class FilterServiceByMasterCommand implements ServletCommand {

    private static final Logger LOGGER = Logger.getLogger(FilterServiceByMasterCommand.class);
    private ServiceService service;
    private ServiceDAO serviceDAO;
    private ServiceMasterService serviceMaster;
    private ServiceMasterDAO serviceMasterDAO;

    private static String page;


    public FilterServiceByMasterCommand() {
        LOGGER.info("Initializing FilterServiceByMasterCommand");

        serviceDAO = ServiceDAO.getInstance();
        service = new ServiceService(serviceDAO);
        serviceMasterDAO = ServiceMasterDAO.getInstance();
        serviceMaster = new ServiceMasterService(serviceMasterDAO);

        ParsePathProperties properties = ParsePathProperties.getInstance();
        page = properties.getProperty("timeTablePage");
    }

    public String execute(HttpServletRequest request, HttpServletResponse response) throws IOException {
        LOGGER.info("Executing FilterServiceByMasterCommand");

        if (request.getParameter("id") == null) {
            LOGGER.info("Parameter id is null");
            return page;
        }
        long master_id = Integer.parseInt(request.getParameter("id"));

        List<Service> serviceList = new ArrayList<>();

        if (master_id != 0) {
            List<ServiceMaster> list = serviceMaster.findServiceMasterByMasterId(master_id);
            for (ServiceMaster sm : list) {
                serviceList.add(service.findServiceById(sm.getService_id()));
            }
        } else {
            serviceList = service.findAll();
        }
        request.setAttribute("services", serviceList);


        PrintWriter out = response.getWriter();
        StringBuilder sb = new StringBuilder();

        sb.append("<div class=\"d-flex flex-wrap align-content-center\">");

        for (Service s : serviceList) {
            sb.append("<div class=\"d-inline-flex p-2 bd-highlight\">");
                sb.append("<div class=\"card bg-light mt-2 mb-2\" style=\"width: 15rem;\">");
                    sb.append("<div class=\"card-body\">");
                        sb.append("<h5 class=\"card-title\">");
                            sb.append(s.getName());
                        sb.append("</h5>");
                        sb.append("<p class=\"card-text\">");
                            sb.append(s.getDescription());
                        sb.append("</p>");
                    sb.append("</div>");
                sb.append("</div>");
            sb.append("</div>");
        }

        sb.append("</div>");


        out.print(sb.toString());

        return null;

    }
}
