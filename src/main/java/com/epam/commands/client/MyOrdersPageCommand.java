package com.epam.commands.client;

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
 * Class that get user orders page
 *
 * @author Volodymyr Tytukh
 */

public class MyOrdersPageCommand implements ServletCommand {

    private static final Logger LOGGER = LogManager.getLogger(MyOrdersPageCommand.class);
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

    public MyOrdersPageCommand() {
        LOGGER.info("Initializing MyOrdersPageCommand");

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
        page = properties.getProperty("userRecordsPage");
    }

    public String execute(HttpServletRequest request, HttpServletResponse response) throws IOException {
        LOGGER.info("Executing MyOrdersPageCommand");

        if (request.getSession().getAttribute("id") == null) {
            LOGGER.info("Parameter id is null");
            return page;
        }
        long user_id = (long) request.getSession().getAttribute("id");

        //pagination
        int pageNumb = 1;
        if (request.getParameter("page") != null)
            pageNumb = Integer.parseInt(request.getParameter("page"));
        int count = record.getCountRecordsByUserId(user_id);
        LOGGER.info("Count of records {}", count);
        int limit = 5;
        int numberPages = (int) Math.ceil((float) count / limit);
        //--------------

        List<Record> records = record.findRecordsByUserIdWithLimit(user_id, (pageNumb - 1) * limit, limit);
        List<ServiceMaster> mastersService = new ArrayList<>();
        List<Status> statuses = new ArrayList<>();
        List<Master> masters = new ArrayList<>();
        List<Service> services = new ArrayList<>();
        List<User> mastersUser = new ArrayList<>();

        for (int i = 0; i < records.size(); i++) {
            mastersService.add(serviceMaster.findServiceMasterById(records.get(i).getMaster_has_service_id()));
            long id = records.get(i).getStatus_id() - 1;
            statuses.add(Status.values()[(int) id]);
            masters.add(master.findMasterById(mastersService.get(i).getMaster_id()));
            services.add(service.findServiceById(mastersService.get(i).getService_id()));
            mastersUser.add(user.findUserById(masters.get(i).getUser_id()));
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
