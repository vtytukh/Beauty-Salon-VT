package com.epam.commands.client;

import com.epam.commands.ServletCommand;
import com.epam.dao.Record.RecordDAO;
import com.epam.dao.ServiceMaster.ServiceMasterDAO;
import com.epam.model.Record;
import com.epam.model.ServiceMaster;
import com.epam.model.Status;
import com.epam.service.RecordService;
import com.epam.service.ServiceMasterService;
import com.epam.utility.ParsePathProperties;
import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.LogManager;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

/**
 * Class that post order
 *
 * @author Volodymyr Tytukh
 */

public class OrderCommand implements ServletCommand {

    private static final Logger LOGGER = LogManager.getLogger(OrderCommand.class);
    private ServiceMasterService serviceMaster;
    private ServiceMasterDAO serviceMasterDAO;
    private RecordService recordS;
    private RecordDAO recordDAO;

    private static String mainPage;

    public OrderCommand() {
        LOGGER.info("Initializing OrderCommand");

        serviceMasterDAO = ServiceMasterDAO.getInstance();
        serviceMaster = new ServiceMasterService(serviceMasterDAO);
        recordDAO = RecordDAO.getInstance();
        recordS = new RecordService(recordDAO);

        ParsePathProperties properties = ParsePathProperties.getInstance();
        mainPage = properties.getProperty("mainPage");
    }

    public String execute(HttpServletRequest request, HttpServletResponse response) throws IOException {
        LOGGER.info("Executing OrderCommand");

        String resultPage = mainPage;

        String masterId = request.getParameter("master-id");
        String serviceId = request.getParameter("service-id");
        String calendar = request.getParameter("calendar");
        String time = request.getParameter("time");

        LOGGER.info("master id = {}", masterId);
        LOGGER.info("serviceId id = {}", serviceId);
        LOGGER.info("calendar id = {}", calendar);
        LOGGER.info("time id = {}", time);
        HttpSession session = request.getSession();

        Object user_id = session.getAttribute("id");
        LOGGER.info("user_id = {}", user_id);


        if (masterId != null
                && serviceId != null
                && calendar != null
                && time != null) {
            LOGGER.info("Order not null");
            Record record = new Record();

            record.setUser_id((long) user_id);
            ServiceMaster sm = serviceMaster.findServiceMasterByMasterAndService(
                    (long) Integer.parseInt(masterId), (long) Integer.parseInt(serviceId));
            record.setMaster_has_service_id(sm.getId());

            record.setTime(calendar + " " + time);
            record.setStatus_id((long) Status.PENDING.ordinal() + 1);

            if (recordS.addRecord(record)) {
                LOGGER.info("Added record successfully");
                response.sendRedirect(request.getContextPath()+"/myOrders?valid_message=added_success");
            } else {
                LOGGER.info("Added record unsuccessfully");
                response.sendRedirect(request.getContextPath()+"/order?valid_message=added_unsuccessful");
            }
            resultPage = null;
        }
        return resultPage;
    }
}
