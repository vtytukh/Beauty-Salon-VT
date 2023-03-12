package com.epam.commands.client;

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
 * Class that update status paid
 *
 * @author Volodymyr Tytukh
 */

public class UpdateStatusPaidCommand implements ServletCommand {

    private static final Logger LOGGER = LogManager.getLogger(UpdateStatusPaidCommand.class);
    private RecordService record;
    private RecordDAO recordDAO;

    private static String page;


    public UpdateStatusPaidCommand() {
        LOGGER.info("Initializing UpdateStatusPaidCommand");

        recordDAO = RecordDAO.getInstance();
        record = new RecordService(recordDAO);

        ParsePathProperties properties = ParsePathProperties.getInstance();
        page = properties.getProperty("userRecordsPage");
    }

    public String execute(HttpServletRequest request, HttpServletResponse response) throws IOException {
        LOGGER.info("Executing UpdateStatusPaidCommand");

        if (request.getParameter("id") != null) {
            long id = Integer.parseInt(request.getParameter("id"));
            if (record.updateStatus(id, Status.PAID)) {
                LOGGER.info("Updating status to paid was successful");
                response.sendRedirect(request.getContextPath()+"/myOrders?valid_message=payment_success");
            } else {
                LOGGER.info("Updating status to paid was unsuccessful");
                response.sendRedirect(request.getContextPath()+"/myOrders?valid_message=payment_unsuccessful");
            }
            page = null;
        }
        return page;
    }
}
