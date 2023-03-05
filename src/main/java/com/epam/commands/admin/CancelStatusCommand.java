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
    public String execute(HttpServletRequest request, HttpServletResponse response) {
        LOGGER.info("Executing CancelStatusCommand");

        String resultPage = recordsPage;
        LOGGER.info("Id parameter " + request.getParameter("id"));
        if (request.getParameter("id") != null) {
            long id = Integer.parseInt(request.getParameter("id"));
            if (record.updateStatus(id, Status.CANCELED)) {
                LOGGER.info("Set status to canceled successful");
                return resultPage;
            }
        }
        LOGGER.info("Set status to canceled unsuccessful");
        return recordsPage;
    }
}
