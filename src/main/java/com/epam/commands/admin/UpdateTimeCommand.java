package com.epam.commands.admin;

import com.epam.commands.ServletCommand;
import com.epam.dao.Record.RecordDAO;
import com.epam.model.Record;
import com.epam.service.RecordService;
import com.epam.utility.ParsePathProperties;
import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.LogManager;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Class that update time in record
 *
 * @author Volodymyr Tytukh
 */

public class UpdateTimeCommand implements ServletCommand {

    private static final Logger LOGGER = LogManager.getLogger(UpdateTimeCommand.class);
    private RecordService record;
    private RecordDAO recordDAO;

    private static String page;


    public UpdateTimeCommand() {
        LOGGER.info("Initializing UpdateTimeCommand");

        recordDAO = RecordDAO.getInstance();
        record = new RecordService(recordDAO);

        ParsePathProperties properties = ParsePathProperties.getInstance();
        page = properties.getProperty("recordsPage");
    }

    public String execute(HttpServletRequest request, HttpServletResponse response) throws IOException {

        LOGGER.info("Executing UpdateTimeCommand");

        if (request.getParameter("id") != null && request.getParameter("time") != null) {
            long record_id = Integer.parseInt(request.getParameter("id"));
            String hour = request.getParameter("time");
            LOGGER.info("Hour {}", hour);

            String time;
            if (hour.length() == 1) {
                time = "0" + hour + ":00";
            }
            else {
                time = hour + ":00";
            }
            LOGGER.info("Time {}", time);
            Record rec = record.findRecord(record_id);

            String date = rec.getTime().substring(0, 11) + time;
            LOGGER.info("Date {}", date);

            if (record.updateTime(record_id, date)) {
                LOGGER.info("Update time was successfully");
                response.sendRedirect(request.getContextPath()+"/admin/records?valid_message=time_updated_success");
            } else {
                LOGGER.info("Update time was unsuccessfully");
                response.sendRedirect(request.getContextPath()+"/admin/records?valid_message=time_updated_unsuccessful");
            }
            page = null;
        }
        return page;
    }
}
