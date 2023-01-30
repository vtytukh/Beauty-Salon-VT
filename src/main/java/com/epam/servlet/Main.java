package com.epam.servlet;

import com.epam.commands.CommandManager;
import com.epam.commands.ServletCommand;
import org.apache.log4j.Logger;

import javax.servlet.Servlet;
import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class Main extends HttpServlet {

    private static final Logger LOGGER = Logger.getLogger(Servlet.class);

    private CommandManager commandManager;

    public void init(ServletConfig config) throws ServletException {
        LOGGER.info("Initializing Servlet");
        commandManager = new CommandManager();
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        LOGGER.info("Processing get request");

        ServletCommand command = commandManager.getGetCommand(request);
        String page = command.execute(request, response);

        if (page != null) {
            if (!response.isCommitted())
                request.getRequestDispatcher(page).forward(request, response);
        }
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        LOGGER.info("Processing post request");

        ServletCommand command = commandManager.getPostCommand(request);
        String page = command.execute(request, response);

        if (page != null) {
            response.sendRedirect(getUrl(page));
        }
    }

    private String getUrl(String page) {
        switch (page) {

            case "/WEB-INF/order.jsp":
            case "/WEB-INF/my-orders.jsp":
                return "/beauty-salon/myOrders";
            case "/WEB-INF/time-table.jsp":
                return "/beauty-salon/master/timeTable";
            case "/WEB-INF/records.jsp":
            case "/WEB-INF/item.jsp":
                return "/beauty-salon/admin/records";
            case "/WEB-INF/index.jsp":
            case "/WEB-INF/login.jsp":
            case "/WEB-INF/register.jsp":
            default:
                return "/beauty-salon";
        }
    }
}
