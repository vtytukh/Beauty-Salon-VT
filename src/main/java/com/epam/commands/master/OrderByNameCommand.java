package com.epam.commands.master;

import com.epam.commands.ServletCommand;
import com.epam.dao.Master.MasterDAO;
import com.epam.model.Master;
import com.epam.service.MasterService;
import com.epam.utility.ParsePathProperties;
import org.apache.log4j.Logger;

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

    private static final Logger LOGGER = Logger.getLogger(OrderByNameCommand.class);
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

        String s1 = request.getParameter("id");
        String s2 = request.getParameter("way");
        List<Master> masterList;

        if (s1 != null && s2 != null) {
            int resultColumn = Integer.parseInt(s1);
            int resultWay = Integer.parseInt(s2);
            boolean isRate = false;
            boolean isDescending = false;
            if (resultWay == 1) isDescending = true;
            if (resultColumn != 0) {
                if (resultColumn == 2) isRate = true;
                masterList = master.findAllWithNameOrderBy(isRate, isDescending);
            } else {
                masterList = master.findAllWithName();
            }
        } else {
            masterList = master.findAllWithName();
        }

        PrintWriter out = response.getWriter();

        StringBuilder sb = new StringBuilder();

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


        out.print(sb.toString());

        return null;

    }
}
