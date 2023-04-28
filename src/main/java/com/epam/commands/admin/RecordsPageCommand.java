package com.epam.commands.admin;

import com.epam.commands.ServletCommand;
import com.epam.dao.Master.MasterDAO;
import com.epam.dao.Record.RecordDAO;
import com.epam.dao.Service.ServiceDAO;
import com.epam.dao.ServiceMaster.ServiceMasterDAO;
import com.epam.dao.User.UserDAO;
import com.epam.model.*;
import com.epam.service.*;
import com.epam.utility.ParsePathProperties;
import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.LogManager;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * Class that get all record with pagination
 *
 * @author Volodymyr Tytukh
 */

public class RecordsPageCommand implements ServletCommand {

    private static final Logger LOGGER = LogManager.getLogger(RecordsPageCommand.class);
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
    private static String pageDetails;


    public RecordsPageCommand() {
        LOGGER.info("Initializing RecordsPageCommand");

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
        page = properties.getProperty("recordsPage");
        pageDetails = properties.getProperty("recordItemPage");
    }

    public String execute(HttpServletRequest request, HttpServletResponse response) throws IOException {
        LOGGER.info("Executing RecordsPageCommand");

        int pageNumb = 1;
        if (request.getParameter("page") != null)
            pageNumb = Integer.parseInt(request.getParameter("page"));
        int count = record.getCountRecords();
        LOGGER.info("Count of records {}", count);
        int limit = 5;
        int numberPages = (int) Math.ceil((float) count / limit);
        List<Record> records = record.findRecordsWithLimit((pageNumb - 1) * limit, limit);


        List<User> users = new ArrayList<>();
        for (int i = 0; i < records.size(); i++) {
            users.add(user.findUserById(records.get(i).getUser_id()));
        }
        List<ServiceMaster> mastersService = new ArrayList<>();
        for (int i = 0; i < records.size(); i++) {
            mastersService.add(serviceMaster.findServiceMasterById(records.get(i).getMaster_has_service_id()));
        }
        List<Status> statuses = new ArrayList<>();
        for (int i = 0; i < records.size(); i++) {
            long id = records.get(i).getStatus_id() - 1;
            statuses.add(Status.values()[(int) id]);
        }


        List<Master> masters = new ArrayList<>();
        for (int i = 0; i < mastersService.size(); i++) {
            masters.add(master.findMasterById(mastersService.get(i).getMaster_id()));
        }
        List<Service> services = new ArrayList<>();
        for (int i = 0; i < mastersService.size(); i++) {
            services.add(service.findServiceById(mastersService.get(i).getService_id()));
        }

        List<User> mastersUser = new ArrayList<>();
        for (int i = 0; i < masters.size(); i++) {
            mastersUser.add(user.findUserById(masters.get(i).getUser_id()));
        }

        for (int i = 0; i < records.size(); i++) {
            records.get(i).setUser(users.get(i));
            records.get(i).setUserMaster(mastersUser.get(i));
            records.get(i).setService(services.get(i));
            records.get(i).setServiceMaster(mastersService.get(i));
            records.get(i).setStatus(statuses.get(i));
        }

        request.setAttribute("records", records);

        //----------------
        request.setAttribute("noOfPages", numberPages);
        request.setAttribute("currentPage", pageNumb);
        //----------------

        return page;
    }
}
