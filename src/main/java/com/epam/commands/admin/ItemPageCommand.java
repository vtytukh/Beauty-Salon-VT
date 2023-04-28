package com.epam.commands.admin;

import com.epam.commands.ServletCommand;
import com.epam.dao.Master.MasterDAO;
import com.epam.dao.Record.RecordDAO;
import com.epam.dao.Service.ServiceDAO;
import com.epam.dao.ServiceMaster.ServiceMasterDAO;
import com.epam.dao.User.UserDAO;
import com.epam.model.*;
import com.epam.service.*;
import com.epam.utility.MasterTime;
import com.epam.utility.ParsePathProperties;
import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.LogManager;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Class that get record by id
 *
 * @author Volodymyr Tytukh
 */

public class ItemPageCommand implements ServletCommand {

    private static final Logger LOGGER = LogManager.getLogger(ItemPageCommand.class);
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
    private static String pageRe;


    public ItemPageCommand() {
        LOGGER.info("Initializing ItemPageCommand");

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
        page = properties.getProperty("recordItemPage");
        pageRe = properties.getProperty("usersPage");
    }

    public String execute(HttpServletRequest request, HttpServletResponse response) throws IOException {
        LOGGER.info("Executing ItemPageCommand");

        if (request.getParameter("id") == null) {
            LOGGER.info("Parameter is null");
            return pageRe;
        }

        long id = Integer.parseInt(request.getParameter("id"));
        Record rec = record.findRecord(id);

        if (rec == null || rec.getId() == null) {
            LOGGER.info("Wrong parameter");
            return pageRe;
        }

        User userName = user.findUserById(rec.getUser_id());
        ServiceMaster sm = serviceMaster.findServiceMasterById(rec.getMaster_has_service_id());
        long status_id = rec.getStatus_id() - 1;
        Status status = Status.values()[(int) status_id];
        Master mas = master.findMasterById(sm.getMaster_id());
        Service ser = service.findServiceById(sm.getService_id());
        User userMaster = user.findUserById(mas.getUser_id());
        rec.setUser(userName);
        rec.setUserMaster(userMaster);
        rec.setService(ser);
        rec.setServiceMaster(sm);
        rec.setStatus(status);

        List<ServiceMaster> list = serviceMaster.findServiceMasterByMasterId(sm.getMaster_id());
        List<Record> recs = new ArrayList<>();
        for (ServiceMaster s : list) {
            recs.addAll(record.findAllRecordsTime(s.getId(), rec.getTime().substring(0, 10), false));
        }

        String date = rec.getTime().substring(0, 11);
        List<Integer> freeHours = MasterTime.getFreeHours(recs);

        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
        LocalDateTime now = LocalDateTime.now();
        String dateNow = dtf.format(now);

        if (date.equals(dateNow.substring(0, 11))) {
            freeHours = freeHours.stream()
                    .filter(n -> n > Integer.parseInt(dateNow.substring(11, 13)))
                    .collect(Collectors.toList());
        } else if (date.compareTo(dateNow.substring(0, 11)) < 0) {
            freeHours = null;
        }

        LOGGER.info("Getting item successful");
        request.setAttribute("record", rec);
        request.setAttribute("recTime", freeHours);

        return page;
    }
}
