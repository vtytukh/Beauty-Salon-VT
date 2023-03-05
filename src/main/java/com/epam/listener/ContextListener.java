package com.epam.listener;

import com.epam.utility.ParsePathProperties;
import com.epam.utility.email.EmailController;
import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.LogManager;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

public class ContextListener implements ServletContextListener {

    private static final Logger LOGGER = LogManager.getLogger(ContextListener.class);

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
