package com.epam.commands.master;

import com.epam.commands.ServletCommand;
import com.epam.dao.Record.RecordDAO;
import com.epam.model.Status;
import com.epam.service.RecordService;
import com.epam.utility.ParsePathProperties;
import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.LogManager;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Class that update status record to finished
 *
 * @author Volodymyr Tytukh
 */

public class UpdateStatusCommand implements ServletCommand {

    private static final Logger LOGGER = LogManager.getLogger(UpdateStatusCommand.class);
    private RecordService record;
    private RecordDAO recordDAO;

    private static String page;


    public UpdateStatusCommand() {
        LOGGER.info("Initializing UpdateStatusCommand");

        recordDAO = RecordDAO.getInstance();
        record = new RecordService(recordDAO);

        ParsePathProperties properties = ParsePathProperties.getInstance();
        page = properties.getProperty("timeTablePage");
    }

    public String execute(HttpServletRequest request, HttpServletResponse response) throws IOException {
        LOGGER.info("Executing UpdateStatusCommand");

        if (request.getParameter("id") != null) {
            long id = Integer.parseInt(request.getParameter("id"));
            if (record.updateStatus(id, Status.FINISHED)) {
                LOGGER.info("Updating status to finished was successful");
                response.sendRedirect(request.getContextPath()+"/master/timeTable?valid_message=finished_success");
            } else {
                LOGGER.info("Updating status to finished was unsuccessful");
                response.sendRedirect(request.getContextPath()+"/master/timeTable?valid_message=finished_unsuccessful");
            }
            page = null;
        }
        return page;
    }
}