package com.epam.commands;

import com.epam.commands.admin.*;
import com.epam.commands.auth.*;
import com.epam.commands.client.*;
import com.epam.commands.master.OrderByNameCommand;
import com.epam.commands.master.TimeTablePageCommand;
import com.epam.commands.master.UpdateStatusCommand;
import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.LogManager;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;

/**
 * Class that manage commands by request
 *
 * @author Volodymyr Tytukh
 */

public class CommandManager {

    private static final Logger LOGGER = LogManager.getLogger(CommandManager.class);

    private HashMap<String, ServletCommand> getCommands;
    private HashMap<String, ServletCommand> postCommands;
    private static String errorPage;

    public CommandManager() {
        LOGGER.info("Initializing CommandManager");

        getCommands = new HashMap<>();
        postCommands = new HashMap<>();

        getCommands.put("/", new MainPageCommand());
        getCommands.put("/login", new LoginPageCommand());

        getCommands.put("/register", new RegisterPageCommand());

        getCommands.put("/order", new OrderPageCommand());
        getCommands.put("/order/masters", new GetMastersCommand());
        getCommands.put("/order/time", new GetTimeCommand());
        getCommands.put("/order/comment", new CommentPageCommand());

        getCommands.put("/filterServices", new FilterServiceByMasterCommand());
        getCommands.put("/filterMasters", new FilterMasterByServiceCommand());

        getCommands.put("/orderBy", new OrderByNameCommand());

        getCommands.put("/myOrders", new MyOrdersPageCommand());


        getCommands.put("/admin/records", new RecordsPageCommand());
        getCommands.put("/admin/records/edit", new ItemPageCommand());

        getCommands.put("/admin/users", new UsersPageCommand());
        getCommands.put("/admin/users/edit", new UsersItemPageCommand());
        getCommands.put("/admin/users/getService", new GetServicesCommand());

        getCommands.put("/master/timeTable", new TimeTablePageCommand());
        getCommands.put("/admin/createService", new CreateServicePageCommand());

        getCommands.put("/language", new ChangeLanguageCommand());

        postCommands.put("/login", new LoginCommand());
        postCommands.put("/logout", new LogoutCommand());
        postCommands.put("/register", new RegisterCommand());
        postCommands.put("/order", new OrderCommand());
        postCommands.put("/order/comment", new CommentCommand());

        postCommands.put("/master/timeTable/updateStatus", new UpdateStatusCommand());
        postCommands.put("/myOrders/paid", new UpdateStatusPaidCommand());
        postCommands.put("/admin/createService", new CreateServiceCommand());

        postCommands.put("/admin/users", new UsersPageCommand());
        postCommands.put("/admin/users/setService", new UsersCommand());
        postCommands.put("/admin/users/setMaster", new SetMasterRoleCommand());

        postCommands.put("/admin/records/cancel", new CancelStatusCommand());
        postCommands.put("/admin/records/accept", new AcceptedStatusCommand());
        postCommands.put("/admin/records/updateTime", new UpdateTimeCommand());

        postCommands.put("/language", new ChangeLanguageCommand());


//        ParseProperties properties = ParseProperties.getInstance();
//        errorPage = properties.getProperty("errorPage");
    }

    public ServletCommand getGetCommand(HttpServletRequest request) {
        String command = getMapping(request);
        LOGGER.info("Getting command {}", command);

        if (getCommands.get(command) == null) {
            return getCommands.get("/");
        }

        return getCommands.get(command);
    }


    public ServletCommand getPostCommand(HttpServletRequest request) {
        String command = getMapping(request);
        LOGGER.info("Getting command {}", command);

        if (postCommands.get(command) == null) {
            return getCommands.get("/");
        }

        return postCommands.get(command);
    }


    public String getMapping(HttpServletRequest request) {
        String mapping = request.getRequestURI().substring(request.getContextPath().length());
        if (mapping.endsWith("/")) {
            mapping = mapping.substring(0, mapping.length() - 1);
        }

        return mapping;
    }
}