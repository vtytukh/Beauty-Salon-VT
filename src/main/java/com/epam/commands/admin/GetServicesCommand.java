package com.epam.commands.admin;

import com.epam.commands.ServletCommand;
import com.epam.dao.Master.MasterDAO;
import com.epam.dao.Service.ServiceDAO;
import com.epam.dao.ServiceMaster.ServiceMasterDAO;
import com.epam.model.Master;
import com.epam.model.Service;
import com.epam.model.ServiceMaster;
import com.epam.service.MasterService;
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
 * Class that print services in select tag
 *
 * @author Volodymyr Tytukh
 */


public class GetServicesCommand implements ServletCommand {

    private static final Logger LOGGER = Logger.getLogger(GetServicesCommand.class);
    private ServiceService service;
    private ServiceDAO serviceDAO;
    private MasterService master;
    private MasterDAO masterDAO;
    private ServiceMasterService serviceMaster;
    private ServiceMasterDAO serviceMasterDAO;
    private static String page;


    public GetServicesCommand() {
        LOGGER.info("Initializing GetServicesCommand");

        serviceDAO = ServiceDAO.getInstance();
        service = new ServiceService(serviceDAO);
        masterDAO = MasterDAO.getInstance();
        master = new MasterService(masterDAO);
        serviceMasterDAO = ServiceMasterDAO.getInstance();
        serviceMaster = new ServiceMasterService(serviceMasterDAO);
        ParsePathProperties properties = ParsePathProperties.getInstance();
        page = properties.getProperty("usersPage");
    }

    public String execute(HttpServletRequest request, HttpServletResponse response) throws IOException {
        LOGGER.info("Executing GetServicesCommand");

        List<Service> list = new ArrayList<>();
        if (request.getParameter("id") != null) {

            long user_id = Integer.parseInt(request.getParameter("id"));
            Master mas = master.findMasterByUserId(user_id);

            List<ServiceMaster> sm = new ArrayList<>();
            if (mas != null && mas.getId() != null) {
                sm = serviceMaster.findServiceMasterByMasterId(mas.getId());
            }
            list = service.findAll();

            List<Service> result = new ArrayList<>();
            for (ServiceMaster a : sm) {
                for (Service b : list) {
                    if (a.getService_id() == b.getId()) {
                        result.add(b);
                    }
                }
            }
            list.removeAll(result);

        }

        PrintWriter out = response.getWriter();
        StringBuilder sb = new StringBuilder();

        sb.append("<select name=\"service-id\" form=\"services\" required>\n" +
                "    <option selected disabled>Select service</option>");
        for (Service ser : list) {
            sb.append("<option value=\"").append(ser.getId()).append("\">").append(ser.getName())
                    //.append(request.getParameter("locale").equals("en") ? ser.getName() : ser.getNameUkr())
                    .append("</option>");
        }
        sb.append("</select>");
        out.print(sb.toString());

        return null;
    }
}
