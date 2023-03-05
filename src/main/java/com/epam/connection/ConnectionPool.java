package com.epam.connection;

import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.LogManager;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.SQLException;

public class ConnectionPool {
    private static final Logger LOGGER = LogManager.getLogger(ConnectionPool.class);

    private static ConnectionPool connectionPool = null;
    private DataSource dataSource = null;

    private ConnectionPool() {
        LOGGER.info("Initializing ConnectionPool class");
    }

    public Connection getConnection() {
        Context ctx;
        Connection c = null;
        try {
            ctx = new InitialContext();
            DataSource ds = (DataSource) ctx.lookup("java:comp/env/jdbc/beauty_salon");
            c = ds.getConnection();
        } catch (NamingException e) {
            LOGGER.error(e.getMessage());
            e.printStackTrace();
        } catch (SQLException e) {
            LOGGER.error(e.getMessage());
            e.printStackTrace();
        }
        return c;
    }

    public static synchronized ConnectionPool getInstance() {
        if (connectionPool == null) {
            connectionPool = new ConnectionPool();
        }
        return connectionPool;
    }
}
