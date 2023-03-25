package com.epam.commands.master;

import com.epam.commands.ServletCommand;
import com.epam.dao.Master.MasterDAO;
import com.epam.model.Master;
import com.epam.service.MasterService;
import com.epam.utility.ParsePathProperties;
import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.LogManager;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

/**
 * Class that order masters by name
 *
 * @author Volodymyr Tytukh
 */

public class OrderByNameCommand implements ServletCommand {

    private static final Logger LOGGER = LogManager.getLogger(OrderByNameCommand.class);
    private MasterService master;
    private MasterDAO masterDAO;

    private static String page;


    public OrderByNameCommand() {
        LOGGER.info("Initializing OrderByNameCommand");

        masterDAO = MasterDAO.getInstance();
        master = new MasterService(masterDAO);

        ParsePathProperties properties = ParsePathProperties.getInstance();
        page = properties.getProperty("timeTablePage");
    }

    public String execute(HttpServletRequest request, HttpServletResponse response) throws IOException {
        LOGGER.info("Executing command");

        String columnStr = request.getParameter("column");
        String wayStr = request.getParameter("way");
        String serviceStr = request.getParameter("service");
        List<Master> masterList;

        if (columnStr != null && wayStr != null && serviceStr != null) {
            int resultColumn = Integer.parseInt(columnStr);
            int resultWay = Integer.parseInt(wayStr);
            Long serviceId = Long.parseLong(serviceStr);

            boolean isRate = resultColumn == 2;
            boolean isDescending = resultWay == 1;

            if (serviceId != 0) {
                masterList = master.findMastersWithNameByServiceId(isRate, isDescending, serviceId);
            } else {
                masterList = master.findMastersWithNameOrderBy(isRate, isDescending);
            }
        } else {
            masterList = master.findAllWithName();
        }

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
            sb.append("<p class=\"card-text text-center\">");
            for (int i = 1; i <= 5; i++) {
                if (i <= m.getMark()) {
                    sb.append("<i class=\"bi bi-star-fill text-warning\"></i> ");
                } else if ((i > m.getMark()) && (i - m.getMark() > 0) && (i - m.getMark() < 1)) {
                    sb.append("<i class=\"bi bi-star-half text-warning\"></i> ");
                } else {
                    sb.append("<i class=\"bi bi-star text-warning\"></i> ");
                }
            }
            sb.append("<br/>").append(m.getMark());
            sb.append("</p>");
            sb.append("</div>");
            sb.append("</div>");
            sb.append("</div>");
        }
        sb.append("</div>");

        out.print(sb);

        return null;

    }
}
