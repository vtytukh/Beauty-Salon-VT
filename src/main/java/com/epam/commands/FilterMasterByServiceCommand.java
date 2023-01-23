package com.epam.commands;

import com.epam.dao.Master.MasterDAO;
import com.epam.dao.ServiceMaster.ServiceMasterDAO;
import com.epam.model.Master;
import com.epam.model.ServiceMaster;
import com.epam.service.MasterService;
import com.epam.service.ServiceMasterService;
import com.epam.utility.ParsePathProperties;
import org.apache.log4j.Logger;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;

/**
 * Class that filter master by service
 *
 * @author Volodymyr Tytukh
 */

public class FilterMasterByServiceCommand implements ServletCommand {

    private static final Logger LOGGER = Logger.getLogger(FilterMasterByServiceCommand.class);
    private MasterService master;
    private MasterDAO masterDAO;
    private ServiceMasterService serviceMaster;
    private ServiceMasterDAO serviceMasterDAO;

    private static String page;


    public FilterMasterByServiceCommand() {
        LOGGER.info("Initializing FilterMasterByServiceCommand");

        masterDAO = MasterDAO.getInstance();
        master = new MasterService(masterDAO);
        serviceMasterDAO = ServiceMasterDAO.getInstance();
        serviceMaster = new ServiceMasterService(serviceMasterDAO);

        ParsePathProperties properties = ParsePathProperties.getInstance();
        page = properties.getProperty("timeTablePage");
    }

    public String execute(HttpServletRequest request, HttpServletResponse response) throws IOException {
        LOGGER.info("Executing FilterMasterByServiceCommand");

        if (request.getParameter("id") == null) {
            LOGGER.info("Parameter id is null");
            return page;
        }
        long service_id = Integer.parseInt(request.getParameter("id"));

        List<Master> masterList = new ArrayList<>();

        if (service_id != 0) {
            List<ServiceMaster> list = serviceMaster.findMastersByService(service_id);
            for (ServiceMaster sm : list) {
                masterList.add(master.findMasterWithNameById(sm.getMaster_id()));
            }
        } else {
            masterList = master.findAllWithName();
        }
        request.setAttribute("masters", masterList);


        PrintWriter out = response.getWriter();

        StringBuilder sb = new StringBuilder();
        sb.append("<div class=\"d-flex flex-wrap align-content-center\">");

        for (Master m : masterList) {
            sb.append("<div class=\"d-inline-flex p-2 bd-highlight\">");
                sb.append("<div class=\"card bg-light mt-2 mb-2\" style=\"width: 15rem;\">");
                    sb.append("<div class=\"card-body\">");
                        sb.append("<h5 class=\"card-title\">");
                            sb.append(m.getUser().getFirstName()).append(" ").append(m.getUser().getLastName());
                        sb.append("</h5>");
                        sb.append("<p class=\"card-text\">");
                            sb.append(m.getMark());
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
