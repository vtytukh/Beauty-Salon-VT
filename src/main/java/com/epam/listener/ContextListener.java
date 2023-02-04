package com.epam.listener;

import com.epam.utility.ParsePathProperties;
import com.epam.utility.email.EmailController;
import org.apache.log4j.Logger;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

public class ContextListener implements ServletContextListener {

    private static final Logger LOGGER = Logger.getLogger(ContextListener.class);

    private EmailController emailController;

    @Override
    public void contextInitialized(ServletContextEvent sce) {
        LOGGER.info("Initializing Servlet Context");
        emailController = new EmailController();
        emailController.startScheduler();
    }

    @Override
    public void contextDestroyed(ServletContextEvent sce) {
        LOGGER.info("Destroying Servlet Context");
        emailController.shutdownScheduler();
    }
}
