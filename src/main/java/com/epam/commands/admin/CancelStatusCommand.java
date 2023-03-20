package com.epam.commands.admin;

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
 * Class that set status to cancel of record
 *
 * @author Volodymyr Tytukh
 */
public class CancelStatusCommand implements ServletCommand {

    private static final Logger LOGGER = LogManager.getLogger(CancelStatusCommand.class);
    private static RecordDAO recordDAO;
    private static RecordService record;

    private static String recordsPage;
    private static String mainPage;

    public CancelStatusCommand() {
        recordDAO = RecordDAO.getInstance();
        record = new RecordService(recordDAO);

        ParsePathProperties properties = ParsePathProperties.getInstance();
        recordsPage = properties.getProperty("recordsPage");
        mainPage = properties.getProperty("mainPage");
    }

    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) throws IOException {
        LOGGER.info("Executing CancelStatusCommand");

        String resultPage = recordsPage;

        if (request.getParameter("id") != null) {
            long id = Integer.parseInt(request.getParameter("id"));
            if (record.updateStatus(id, Status.CANCELED)) {
                LOGGER.info("Set status to canceled successful");
                response.sendRedirect(request.getContextPath()+"/admin/records?valid_message=canceled_success");
            } else {
                LOGGER.info("Set status to canceled unsuccessful");
                response.sendRedirect(request.getContextPath()+"/admin/records?valid_message=canceled_unsuccessful");
            }
            resultPage = null;
        }
        return resultPage;
    }
}
