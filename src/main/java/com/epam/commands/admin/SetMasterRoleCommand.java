package com.epam.commands.admin;

import com.epam.commands.ServletCommand;
import com.epam.dao.Master.MasterDAO;
import com.epam.dao.User.UserDAO;
import com.epam.model.Role;
import com.epam.service.MasterService;
import com.epam.service.UserService;
import com.epam.utility.ParsePathProperties;
import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.LogManager;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Class that set user as master and add master
 *
 * @author Volodymyr Tytukh
 */

public class SetMasterRoleCommand implements ServletCommand {

    private static final Logger LOGGER = LogManager.getLogger(SetMasterRoleCommand.class);
    private MasterService master;
    private MasterDAO masterDAO;
    private UserService user;
    private UserDAO userDAO;

    private static String page;
    private static String pageRe;

    public SetMasterRoleCommand() {
        LOGGER.info("Initializing SetMasterRoleCommand");

        userDAO = UserDAO.getInstance();
        user = new UserService(userDAO);
        masterDAO = MasterDAO.getInstance();
        master = new MasterService(masterDAO);

        ParsePathProperties properties = ParsePathProperties.getInstance();
        page = properties.getProperty("usersItemPage");
        pageRe = properties.getProperty("usersPage");
    }

    public String execute(HttpServletRequest request, HttpServletResponse response) throws IOException {
        LOGGER.info("Executing SetMasterRoleCommand");

        if (request.getParameter("id") != null) {
            long user_id = Long.parseLong(request.getParameter("id"));

            if (user.updateRole(user_id, Role.MASTER)) {
                if (master.addMaster(user_id)) {
                    LOGGER.info("Master role was added successfully");
                    response.sendRedirect(request.getContextPath()+"/admin/users?valid_message=master_added_success");
                } else {
                    LOGGER.info("Adding master role was unsuccessfully");
                    response.sendRedirect(request.getContextPath()+"/admin/users?valid_message=master_added_unsuccessful");
                }
            }
            page = null;
        } else return pageRe;

        return page;
    }
}
