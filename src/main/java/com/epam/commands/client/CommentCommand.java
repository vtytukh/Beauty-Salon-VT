package com.epam.commands.client;

import com.epam.commands.ServletCommand;
import com.epam.dao.Master.MasterDAO;
import com.epam.dao.Record.RecordDAO;
import com.epam.dao.ServiceMaster.ServiceMasterDAO;
import com.epam.model.ServiceMaster;
import com.epam.model.Status;
import com.epam.service.MasterService;
import com.epam.service.RecordService;
import com.epam.service.ServiceMasterService;
import com.epam.utility.ParsePathProperties;
import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.LogManager;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * Class that post a mark to master and update his rate
 *
 * @author Volodymyr Tytukh
 */

public class CommentCommand implements ServletCommand {

    private static final Logger LOGGER = LogManager.getLogger(CommentCommand.class);
    private MasterService master;
    private MasterDAO masterDAO;
    private ServiceMasterService serviceMaster;
    private ServiceMasterDAO serviceMasterDAO;
    private RecordService record;
    private RecordDAO recordDAO;

    private static String page;


    public CommentCommand() {
        LOGGER.info("Initializing CommentCommand");

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
        LOGGER.info("Executing CommentCommand");

        long id = Integer.parseInt(request.getParameter("id"));
        long master_id = Integer.parseInt(request.getParameter("master"));
        int mark = Integer.parseInt(request.getParameter("mark"));
        String feedback = request.getParameter("feedback");

        if(!record.updateMark(id, mark, feedback)) return page;

        List<ServiceMaster> list = serviceMaster.findServiceMasterByMasterId(master_id);

        List<Long> sm = new ArrayList<>();

        for (ServiceMaster s : list) {
            sm.add(s.getId());
        }

        float avgMark = record.getAvgRecords(sm);

        LOGGER.info("Avg mark => " + avgMark);
        if (master.updateMasterRate(master_id, avgMark)) {
            LOGGER.info("Updating master rate successful");
        } else LOGGER.info("Updating master rate unsuccessful");

        LOGGER.info("Update status => " + Status.FEEDBACKED);
        if (record.updateStatus(id, Status.FEEDBACKED)) {
            LOGGER.info("Updating record status successful");
        } else LOGGER.info("Updating record status unsuccessful");

        return page;
    }
}
