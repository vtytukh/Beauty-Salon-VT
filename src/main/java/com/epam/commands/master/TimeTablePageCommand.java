package com.epam.commands.master;

import com.epam.commands.ServletCommand;
import com.epam.dao.Master.MasterDAO;
import com.epam.dao.Record.RecordDAO;
import com.epam.dao.Service.ServiceDAO;
import com.epam.dao.ServiceMaster.ServiceMasterDAO;
import com.epam.dao.User.UserDAO;
import com.epam.model.Master;
import com.epam.model.Record;
import com.epam.model.ServiceMaster;
import com.epam.model.Status;
import com.epam.service.*;
import com.epam.utility.MasterTime;
import com.epam.utility.ParsePathProperties;
import org.apache.log4j.Logger;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

/**
 * Class that get master's time table
 *
 * @author Volodymyr Tytukh
 */

public class TimeTablePageCommand implements ServletCommand {

    private static final Logger LOGGER = Logger.getLogger(TimeTablePageCommand.class);
    private ServiceService service;
    private ServiceDAO serviceDAO;
    private MasterService master;
    private MasterDAO masterDAO;
    private ServiceMasterService serviceMaster;
    private ServiceMasterDAO serviceMasterDAO;
    private RecordService record;
    private RecordDAO recordDAO;
    private UserService user;
    private UserDAO userDAO;

    private static String page;


    public TimeTablePageCommand() {
        LOGGER.info("Initializing TimeTablePageCommand");

        userDAO = UserDAO.getInstance();
        user = new UserService(userDAO);
        serviceDAO = ServiceDAO.getInstance();
        service = new ServiceService(serviceDAO);
        masterDAO = MasterDAO.getInstance();
        master = new MasterService(masterDAO);
        serviceMasterDAO = ServiceMasterDAO.getInstance();
        serviceMaster = new ServiceMasterService(serviceMasterDAO);
        recordDAO = RecordDAO.getInstance();
        record = new RecordService(recordDAO);

        ParsePathProperties properties = ParsePathProperties.getInstance();
        page = properties.getProperty("timeTablePage");
    }

    public String execute(HttpServletRequest request, HttpServletResponse response) throws IOException {
        LOGGER.info("Executing TimeTablePageCommand");

        HttpSession session = request.getSession();
        Master mas = master.findMasterByUserId((long) session.getAttribute("id"));


        List<ServiceMaster> listSm = serviceMaster.findServiceMasterByMasterId(mas.getId());

        String time = request.getParameter("date");
        if (time == null) {
            DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy-MM-dd");
            LocalDateTime now = LocalDateTime.now();
            time = dtf.format(now);
        }

        List<Record> records = new ArrayList<>();
        for (ServiceMaster sm : listSm) {
            records.addAll(record.findAllRecordsTime(sm.getId(), time, true));
            sm.setService(service.findServiceById(sm.getService_id()));
        }

        for (Record r : records) {
            for (ServiceMaster sm : listSm) {
                if (r.getMaster_has_service_id() == sm.getId()) r.setServiceMaster(sm);
            }
        }

        for (Record r : records) {
            r.setStatus(Status.values()[Integer.parseInt(r.getStatus_id() + "") - 1]);
        }

        for (Record r : records) {
            r.setUser(user.findUserById(r.getUser_id()));
        }

        List<Record> recWithEmptySpace = MasterTime.getRecordsWithEmptySpace(records);

        request.setAttribute("records", recWithEmptySpace);
        request.setAttribute("requestedDate", time);

        return page;
    }
}
